package com.relics.backend.database.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Creator {

    protected final String directoryWithJSONFiles;

    public Creator(String directoryWithJSONFiles) {
        this.directoryWithJSONFiles = directoryWithJSONFiles;
    }

    protected List<String> getFileNames() {
        List<String> result = new ArrayList<>();
        File directory = new File(this.directoryWithJSONFiles);
        for (File file : directory.listFiles()) {
            result.add(file.getName());
        }
        return result;
    }

    protected String getRelicAsString(String fileName) {
        String path = this.directoryWithJSONFiles + "\\" + fileName;
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected JSONObject convertToJSON(String relicAsString) {
        try {
            return new JSONObject(relicAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
