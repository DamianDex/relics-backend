package com.relics.backend.database.utils;

import com.relics.backend.model.Category;
import com.relics.backend.model.GeographicLocation;
import com.relics.backend.model.Relic;
import com.relics.backend.repository.CategoryRepository;
import org.codehaus.jettison.json.JSONArray;
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

    public List<Relic> getAllRelicsToSaveInDB(CategoryRepository categoryRepository) throws JSONException {
        List<String> fileNames = this.getFileNames();
        List<Relic> results = new ArrayList<>();
        for (String fileName : fileNames) {

            try {

                String relicAsString = this.getRelicAsString(fileName);
                JSONObject relicAsJSON = this.convertToJSON(relicAsString);

                Relic relic = new Relic();
                GeographicLocation geographicLocation = getGeographicLocation(relicAsJSON);
                Set<Category> categorySet = getCategories(categoryRepository, relicAsJSON);

                relic.setIdentification(relicAsJSON.getString("identification"));
                relic.setDatingOfObject(relicAsJSON.getString("dating_of_obj"));
                relic.setDescription(relicAsJSON.getString("description"));
                relic.setRegisterNumber(relicAsJSON.getString("register_number"));

                relic.setCategories(categorySet);
                relic.setGeographicLocation(geographicLocation);
                results.add(relic);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private Set<Category> getCategories(CategoryRepository categoryRepository, JSONObject relicAsJSON) throws JSONException {
        Set<Category> categorySet = new HashSet<>();
        JSONArray jsonArray = (JSONArray) relicAsJSON.get("categories");
        for (int i = 0; i < jsonArray.length(); i++) {
            String categoryName = jsonArray.getString(i).replace("_", " ");
            categorySet.add(categoryRepository.getOne(categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1)));
        }
        return categorySet;
    }

    private GeographicLocation getGeographicLocation(JSONObject relicAsJSON) throws JSONException {
        GeographicLocation geographicLocation = new GeographicLocation();
        geographicLocation.setStreet(relicAsJSON.getString("street"));
        geographicLocation.setPlaceName(relicAsJSON.getString("place_name"));
        geographicLocation.setCommuneName(relicAsJSON.getString("commune_name"));
        geographicLocation.setDistrictName(relicAsJSON.getString("district_name"));
        geographicLocation.setVoivodeshipName(relicAsJSON.getString("voivodeship_name"));
        geographicLocation.setLatitude(relicAsJSON.getDouble("latitude"));
        geographicLocation.setLongitude(relicAsJSON.getDouble("longitude"));
        return geographicLocation;
    }
}
