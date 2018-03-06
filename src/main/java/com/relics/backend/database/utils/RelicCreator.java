package com.relics.backend.database.utils;

import com.relics.backend.model.Category;
import com.relics.backend.model.GeographicLocation;
import com.relics.backend.model.Relic;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RelicCreator extends Creator {

    public RelicCreator(String directoryWithJSONFiles) {
        super(directoryWithJSONFiles);
    }

    public List<Relic> getAllRelicsToSaveInDB() throws JSONException {
        List<String> fileNames = this.getFileNames();
        List<Relic> results = new ArrayList<>();
        for (String fileName : fileNames) {

            try {

                String relicAsString = this.getRelicAsString(fileName);
                JSONObject relicAsJSON = this.convertToJSON(relicAsString);

                Relic relic = new Relic();

                GeographicLocation geographicLocation = new GeographicLocation();
                geographicLocation.setStreet(relicAsJSON.getString("street"));
                geographicLocation.setPlaceName(relicAsJSON.getString("place_name"));
                geographicLocation.setCommuneName(relicAsJSON.getString("commune_name"));
                geographicLocation.setDistrictName(relicAsJSON.getString("district_name"));
                geographicLocation.setVoivodeshipName(relicAsJSON.getString("voivodeship_name"));
                geographicLocation.setLatitude(relicAsJSON.getDouble("latitude"));
                geographicLocation.setLongitude(relicAsJSON.getDouble("longitude"));

                Set<Category> categorySet = new HashSet<>();
                categorySet.add(new Category("Katolicki", "Brak opisu"));
                relic.setCategories(categorySet);
                relic.setGeographicLocation(geographicLocation);
                results.add(relic);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
