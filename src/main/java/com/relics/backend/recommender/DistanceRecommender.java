package com.relics.backend.recommender;

import com.relics.backend.model.GeographicLocation;
import com.relics.backend.model.Relic;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DistanceRecommender {

    @Autowired
    RelicRepository relicRepository;

    public List<BigInteger> getRandomRelicsIDsByDistance(Integer quantity, Double latitude, Double longitude, Integer maxDistance) {

        List<BigInteger> results = new ArrayList<>(3);

        List<BigInteger> relics = relicRepository.getAllIDs();

        Map<Long, Double> distanceMap = prepareMapIdDistance(relics.subList(1, 500), latitude, longitude);

        for (int i = 0; i < 3; i++) {
            Map.Entry<Long, Double> min = Collections.min(distanceMap.entrySet(), new Comparator<Map.Entry<Long, Double>>() {
                public int compare(Map.Entry<Long, Double> entry1, Map.Entry<Long, Double> entry2) {
                    return entry1.getValue().compareTo(entry2.getValue());
                }
            });

            results.add(BigInteger.valueOf(min.getKey()));
            distanceMap.remove(min.getKey());
        }

        return results;
    }

    Map<Long, Double> prepareMapIdDistance(List<BigInteger> relics, Double latitude, Double longitude) {

        Map<Long, Double> results = new HashMap<>();

        for (BigInteger relicId : relics) {
            Relic relic = relicRepository.getOne(Long.valueOf(String.valueOf(relicId)));
            GeographicLocation geographicLocation = relic.getGeographicLocation();
            results.put(relic.getId(), calculateDistanceInKm(geographicLocation.getLatitude(), geographicLocation.getLatitude(), latitude, longitude));
        }

        return results;
    }

    Double calculateDistanceInKm(Double lat1, Double lon1, Double lat2, Double lon2) {
        Integer radius = 6371;
        Double dLat = degToRad(lat2 - lat1);
        Double dLon = degToRad(lon2 - lon1);
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = radius * c;
        return d;
    }

    private Double degToRad(Double deg) {
        return deg * (Math.PI / 180);
    }
}
