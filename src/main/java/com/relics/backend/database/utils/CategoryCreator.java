package com.relics.backend.database.utils;

import com.relics.backend.model.Category;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryCreator extends Creator {

    public CategoryCreator(String directoryWithJSONFiles) {
        super(directoryWithJSONFiles);
    }

    public List<Category> getAllCategoriesToSaveInDB() throws JSONException {
        List<String> fileNames = this.getFileNames();
        List<Category> results = new ArrayList<>();
        for (String fileName : fileNames) {
            String relicAsString = this.getRelicAsString(fileName);
            JSONObject relicAsJSON = this.convertToJSON(relicAsString);

            JSONArray jsonArray = (JSONArray) relicAsJSON.get("categories");
            for (int i = 0; i < jsonArray.length(); i++) {
                Category category = new Category();
                String categoryName = jsonArray.getString(i).replace("_", " ");
                category.setCategoryName(categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1));
                category.setCategoryDescription("Brak opisu");
                System.out.println(category);
                results.add(category);
            }
        }
        return results;
    }
}
