package com.relics.backend.recommender;

import com.relics.backend.model.GeographicLocation;
import com.relics.backend.model.Relic;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class DistanceRecommender {

    @Autowired
    RelicRepository relicRepository;

    private List<BigInteger> currentRandomRelicIDs;
    private List<BigInteger> results;

    public List<BigInteger> getRandomRelicsIDsByDistance(Integer quantity, Double latitude, Double longitude, Integer maxDistance) {
        results = new ArrayList<>();
        while (results.size() < quantity) {
            setRandomRelicIDs(100);
            for (BigInteger relicId : currentRandomRelicIDs) {
                Relic relic = relicRepository.getOne(Long.valueOf(String.valueOf(relicId)));
                GeographicLocation geographicLocation = relic.getGeographicLocation();
                Double relicLongitude = geographicLocation.getLongitude();
                Double relicLatitude = geographicLocation.getLatitude();

                if (maxDistance > calculateDistanceInKm(latitude, longitude, relicLatitude, relicLongitude)) {
                    results.add(relicId);
                    if (results.size() >= quantity)
                        break;
                }
            }
        }
        return results;
    }

    private void setRandomRelicIDs(Integer quantity) {
        currentRandomRelicIDs = relicRepository.getRandomRelicIDs(quantity);
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
