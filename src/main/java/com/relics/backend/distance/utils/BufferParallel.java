package com.relics.backend.distance.utils;

import com.vividsolutions.jts.geom.*;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.util.Converter;
import org.geotools.util.GeometryTypeConverterFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BufferParallel implements Callable<Polygon> {

    private final DefaultGeographicCRS crs = DefaultGeographicCRS.WGS84;
    private final double[][] points;
    private final double buffer;
    private boolean swapCoordinates = true;

    public BufferParallel(double[][] points, double buffer){
        this.points = points;
        this.buffer = buffer;
    }

    @Override
    public Polygon call() {
        return getRouteBuffer(points, buffer);
    }

    public Polygon getRouteBuffer(double[][] points, double bufferValue){
        LineString line = createLinestringFromArray(points);
        SimpleFeatureType TYPE = null;
        try {
            TYPE = DataUtilities.createType("test", "line", "the_geom:LineString");
        } catch (SchemaException e) {
            e.printStackTrace();
        }
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        featureBuilder.add(line);
        SimpleFeature feature = featureBuilder.buildFeature("LineString_Sample");

        SimpleFeature bufferGeom = bufferFeature(feature, Measure.valueOf(bufferValue, SI.KILOMETER));

        GeometryTypeConverterFactory converterFactory = new GeometryTypeConverterFactory();
        Hints hints = new Hints(Hints.CRS, crs);
        Converter converter = converterFactory.createConverter(LineString.class, Polygon.class, hints);
        Polygon polygon = null;
        try {
            polygon = converter.convert(bufferGeom.getDefaultGeometry(), Polygon.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return polygon;
    }

    private LineString createLinestringFromArray(double[][] points){
        List<Coordinate> coordinatesList = new ArrayList<>();
        for (double[] p: points){
            coordinatesList.add(new Coordinate(p[0], p[1]));
        }
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate[] coordinatesArray = new Coordinate[coordinatesList.size()];
        coordinatesArray = coordinatesList.toArray(coordinatesArray);

        return geometryFactory.createLineString(coordinatesArray);
    }

    private SimpleFeature bufferFeature(SimpleFeature feature, Measure<Double, Length> distance) {
        // extract the geometry
        Geometry geom = (Geometry) feature.getDefaultGeometry();
        Geometry pGeom = geom;
        MathTransform toTransform, fromTransform = null;
        // reproject the geometry to a local projection
        if (!(crs instanceof ProjectedCRS)) {

            Point c = geom.getCentroid();
            double x = c.getCoordinate().x;
            double y = c.getCoordinate().y;

            CoordinateReferenceSystem intermediaryReferenceSystem;
            try {
                intermediaryReferenceSystem = getProperEpsg(x, y);
                if (swapCoordinates) {
                    geom.apply(new InvertCoordinateFilter());
                }
                toTransform = CRS.findMathTransform(crs, intermediaryReferenceSystem, false);
                fromTransform = CRS.findMathTransform(intermediaryReferenceSystem, crs, false);
                pGeom = JTS.transform(geom, toTransform);
            } catch (MismatchedDimensionException | TransformException | FactoryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // buffer
        Geometry out = buffer(pGeom, distance.doubleValue(SI.METER));
        Geometry retGeom = out;
        // reproject the geometry to the original projection
        if (!(crs instanceof ProjectedCRS)) {
            try {
                retGeom = JTS.transform(out, fromTransform);
                if (swapCoordinates) {
                    retGeom.apply(new InvertCoordinateFilter());
                }
            } catch (MismatchedDimensionException | TransformException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // return a new feature containing the geom
        SimpleFeatureType schema = feature.getFeatureType();
        SimpleFeatureTypeBuilder ftBuilder = new SimpleFeatureTypeBuilder();
        ftBuilder.setCRS(crs);

        ftBuilder.addAll(schema.getAttributeDescriptors());
        ftBuilder.setName(schema.getName());

        SimpleFeatureType nSchema = ftBuilder.buildFeatureType();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(nSchema);
        List<Object> atts = feature.getAttributes();
        for (int i = 0; i < atts.size(); i++) {
            if (atts.get(i) instanceof Geometry) {
                atts.set(i, retGeom);
            }
        }
        return builder.buildFeature(null, atts.toArray());
    }

    private Geometry buffer(Geometry geom, double dist) {
        return geom.buffer(dist);

    }

    private CoordinateReferenceSystem getProperEpsg(double lat, double lng) throws FactoryException{
        CoordinateReferenceSystem coordinateReferenceSystem;
        if (lng > 13.5 && lng < 16.5){
            coordinateReferenceSystem = PolishEPSG2000.EPSG2176;
        } else if (lng >= 16.5 && lng < 19.5){
            coordinateReferenceSystem = PolishEPSG2000.EPSG2177;
        } else if (lng >= 19.5 && lng < 22.5) {
            coordinateReferenceSystem = PolishEPSG2000.EPSG2178;
        } else if (lng >= 22.5 && lng < 25.5){
            coordinateReferenceSystem = PolishEPSG2000.EPSG2179;
        } else {
            String code = "AUTO:42001," + lat + "," + lng;
            coordinateReferenceSystem = CRS.decode(code);
            swapCoordinates = false;
        }

        return coordinateReferenceSystem;
    }

    private static class InvertCoordinateFilter implements CoordinateFilter {
        public void filter(Coordinate coord) {
            double oldX = coord.x;
            coord.x = coord.y;
            coord.y = oldX;
        }
    }

}
