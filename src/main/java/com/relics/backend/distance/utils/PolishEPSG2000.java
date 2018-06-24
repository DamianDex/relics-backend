package com.relics.backend.distance.utils;

import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CRSFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class PolishEPSG2000 {

    private static final String EPSG_2176_WKT = "PROJCS[\"ETRS89 / Poland CS2000 zone 5\",\n" +
            "    GEOGCS[\"ETRS89\",\n" +
            "        DATUM[\"European_Terrestrial_Reference_System_1989\",\n" +
            "            SPHEROID[\"GRS 1980\",6378137,298.257222101,\n" +
            "                AUTHORITY[\"EPSG\",\"7019\"]],\n" +
            "            TOWGS84[0,0,0,0,0,0,0], \n" +
            "            AUTHORITY[\"EPSG\",\"6258\"]],\n" +
            "        PRIMEM[\"Greenwich\",0,\n" +
            "            AUTHORITY[\"EPSG\",\"8901\"]],\n" +
            "        UNIT[\"degree\",0.01745329251994328,\n" +
            "            AUTHORITY[\"EPSG\",\"9122\"]],\n" +
            "        AUTHORITY[\"EPSG\",\"4258\"]],\n" +
            "    UNIT[\"metre\",1,\n" +
            "        AUTHORITY[\"EPSG\",\"9001\"]],\n" +
            "    PROJECTION[\"Transverse_Mercator\"],\n" +
            "    PARAMETER[\"latitude_of_origin\",0],\n" +
            "    PARAMETER[\"central_meridian\",15],\n" +
            "    PARAMETER[\"scale_factor\",0.999923],\n" +
            "    PARAMETER[\"false_easting\",5500000],\n" +
            "    PARAMETER[\"false_northing\",0],\n" +
            "    AUTHORITY[\"EPSG\",\"2176\"],\n" +
            "    AXIS[\"y\",EAST],\n" +
            "    AXIS[\"x\",NORTH]]";

    private static final String EPSG_2177_WKT = "PROJCS[\"ETRS89 / Poland CS2000 zone 6\",\n" +
            "    GEOGCS[\"ETRS89\",\n" +
            "        DATUM[\"European_Terrestrial_Reference_System_1989\",\n" +
            "            SPHEROID[\"GRS 1980\",6378137,298.257222101,\n" +
            "                AUTHORITY[\"EPSG\",\"7019\"]],\n" +
            "            TOWGS84[0,0,0,0,0,0,0], \n" +
            "            AUTHORITY[\"EPSG\",\"6258\"]],\n" +
            "        PRIMEM[\"Greenwich\",0,\n" +
            "            AUTHORITY[\"EPSG\",\"8901\"]],\n" +
            "        UNIT[\"degree\",0.01745329251994328,\n" +
            "            AUTHORITY[\"EPSG\",\"9122\"]],\n" +
            "        AUTHORITY[\"EPSG\",\"4258\"]],\n" +
            "    UNIT[\"metre\",1,\n" +
            "        AUTHORITY[\"EPSG\",\"9001\"]],\n" +
            "    PROJECTION[\"Transverse_Mercator\"],\n" +
            "    PARAMETER[\"latitude_of_origin\",0],\n" +
            "    PARAMETER[\"central_meridian\",18],\n" +
            "    PARAMETER[\"scale_factor\",0.999923],\n" +
            "    PARAMETER[\"false_easting\",6500000],\n" +
            "    PARAMETER[\"false_northing\",0],\n" +
            "    AUTHORITY[\"EPSG\",\"2177\"],\n" +
            "    AXIS[\"y\",EAST],\n" +
            "    AXIS[\"x\",NORTH]]";

    private static final String EPSG_2178_WKT = "PROJCS[\"ETRS89 / Poland CS2000 zone 7\",\n" +
            "    GEOGCS[\"ETRS89\",\n" +
            "        DATUM[\"European_Terrestrial_Reference_System_1989\",\n" +
            "            SPHEROID[\"GRS 1980\",6378137,298.257222101,\n" +
            "                AUTHORITY[\"EPSG\",\"7019\"]],\n" +
            "            TOWGS84[0,0,0,0,0,0,0], \n" +
            "            AUTHORITY[\"EPSG\",\"6258\"]],\n" +
            "        PRIMEM[\"Greenwich\",0,\n" +
            "            AUTHORITY[\"EPSG\",\"8901\"]],\n" +
            "        UNIT[\"degree\",0.01745329251994328,\n" +
            "            AUTHORITY[\"EPSG\",\"9122\"]],\n" +
            "        AUTHORITY[\"EPSG\",\"4258\"]],\n" +
            "    UNIT[\"metre\",1,\n" +
            "        AUTHORITY[\"EPSG\",\"9001\"]],\n" +
            "    PROJECTION[\"Transverse_Mercator\"],\n" +
            "    PARAMETER[\"latitude_of_origin\",0],\n" +
            "    PARAMETER[\"central_meridian\",21],\n" +
            "    PARAMETER[\"scale_factor\",0.999923],\n" +
            "    PARAMETER[\"false_easting\",7500000],\n" +
            "    PARAMETER[\"false_northing\",0],\n" +
            "    AUTHORITY[\"EPSG\",\"2178\"],\n" +
            "    AXIS[\"y\",EAST],\n" +
            "    AXIS[\"x\",NORTH]]";

    private static final String EPSG_2179_WKT = "PROJCS[\"ETRS89 / Poland CS2000 zone 8\",\n" +
            "    GEOGCS[\"ETRS89\",\n" +
            "        DATUM[\"European_Terrestrial_Reference_System_1989\",\n" +
            "            SPHEROID[\"GRS 1980\",6378137,298.257222101,\n" +
            "                AUTHORITY[\"EPSG\",\"7019\"]],\n" +
            "            TOWGS84[0,0,0,0,0,0,0], \n" +
            "            AUTHORITY[\"EPSG\",\"6258\"]],\n" +
            "        PRIMEM[\"Greenwich\",0,\n" +
            "            AUTHORITY[\"EPSG\",\"8901\"]],\n" +
            "        UNIT[\"degree\",0.01745329251994328,\n" +
            "            AUTHORITY[\"EPSG\",\"9122\"]],\n" +
            "        AUTHORITY[\"EPSG\",\"4258\"]],\n" +
            "    UNIT[\"metre\",1,\n" +
            "        AUTHORITY[\"EPSG\",\"9001\"]],\n" +
            "    PROJECTION[\"Transverse_Mercator\"],\n" +
            "    PARAMETER[\"latitude_of_origin\",0],\n" +
            "    PARAMETER[\"central_meridian\",24],\n" +
            "    PARAMETER[\"scale_factor\",0.999923],\n" +
            "    PARAMETER[\"false_easting\",8500000],\n" +
            "    PARAMETER[\"false_northing\",0],\n" +
            "    AUTHORITY[\"EPSG\",\"2179\"],\n" +
            "    AXIS[\"y\",EAST],\n" +
            "    AXIS[\"x\",NORTH]]";

    public static CoordinateReferenceSystem EPSG2176;
    public static CoordinateReferenceSystem EPSG2177;
    public static CoordinateReferenceSystem EPSG2178;
    public static CoordinateReferenceSystem EPSG2179;

    static {
        CRSFactory factory = ReferencingFactoryFinder.getCRSFactory(null);
        try {
            EPSG2176 = factory.createFromWKT(EPSG_2176_WKT);
            EPSG2177 = factory.createFromWKT(EPSG_2177_WKT);
            EPSG2178 = factory.createFromWKT(EPSG_2178_WKT);
            EPSG2179 = factory.createFromWKT(EPSG_2179_WKT);

        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }
}
