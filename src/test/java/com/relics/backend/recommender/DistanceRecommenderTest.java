package com.relics.backend.recommender;

import org.junit.Test;

public class DistanceRecommenderTest {

    @Test
    public void test1() {
        DistanceRecommender distanceRecommender = new DistanceRecommender();
        Double d = distanceRecommender.calculateDistanceInKm(10.0, 10.0, 15.0, 15.0);
        System.out.println(d);
    }

}