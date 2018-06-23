package com.relics.backend.distance.utils;

import com.relics.backend.model.Relic;
import com.vividsolutions.jts.geom.*;
import org.geotools.referencing.GeodeticCalculator;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DistancePrimaryOperations {

    public double[] getSearchArea(double[][] route, Double buffer){
        double minLat = 100, maxLat = 0, minLen = 100, maxLen = 0;

        for (double[] p: route) {
            if (p[0] < minLat)
                minLat = p[0];
            if (p[0] > maxLat)
                maxLat = p[0];
            if (p[1] < minLen)
                minLen = p[1];
            if (p[1] > maxLen) {
                maxLen = p[1];
            }
        }
        GeodeticCalculator calc = new GeodeticCalculator();
        calc.setStartingGeographicPoint(maxLen, maxLat);
        calc.setDirection(45, buffer*1000*Math.sqrt(2));
        Point2D upperRightPoint = calc.getDestinationGeographicPoint();
        calc.setStartingGeographicPoint(minLen, minLat);
        calc.setDirection(225, buffer*1000*Math.sqrt(2));
        Point2D bottomLeftPoint = calc.getDestinationGeographicPoint();

        return new double[]{bottomLeftPoint.getY(), upperRightPoint.getY(), bottomLeftPoint.getX(), upperRightPoint.getX()};
    }

    public List<Relic> filterRelicsByBuffer(List<Relic> relics, Polygon buffer){
        GeometryFactory geometryFactory = new GeometryFactory();
        return relics.stream()
                .filter(relic ->
                        buffer.contains(geometryFactory.createPoint(
                                            new Coordinate(relic.getGeographicLocation().getLatitude(),
                                                           relic.getGeographicLocation().getLongitude()))))
                .collect(Collectors.toList());
    }

    public double[][] getBufferPoints(Polygon buffer){
        List<double[]> pointsList = Arrays.stream(buffer.getCoordinates())
                .map(coordinate -> new double[]{coordinate.x, coordinate.y})
                .collect(Collectors.toList());
        double[][] bufferPoints = new double[pointsList.size()][2];

        return pointsList.toArray(bufferPoints);
    }

}
