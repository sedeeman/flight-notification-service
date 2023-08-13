package com.sedeeman.ca.util;

import com.sedeeman.ca.exception.DataLoadingException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFileToJsonObject {


    public JSONObject read() {
        String file = "src/main/resources/openapi/response.json";
        try {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            return new JSONObject(content);
        } catch (IOException e) {
            throw new DataLoadingException(String.format("Error loading response types from JSON file : %s", e));
        }

    }

}
