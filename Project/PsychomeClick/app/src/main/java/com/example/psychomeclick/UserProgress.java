package com.example.psychomeclick;

import android.util.JsonReader;

import com.fasterxml.jackson.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProgress{

    private JsonReader jr;
    private static final String filePath="assets/progress";

    public void navigateToQuestion(String subjectName) {
            //find


    }
    public void createSave() {
        System.out.println("begin");
    Map<String, Object> data = new HashMap<>();
        data.put("Quantitative reasoning", new ArrayList<Object>());
        data.put("Verbal reasoning", new ArrayList<Object>());
        data.put("English", new ArrayList<Object>());

    // Specify the file path


    // Create ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

        try {
        // Write the Map to a JSON file
        objectMapper.writeValue(new File(filePath), data);
        System.out.println("JSON file '" + filePath + "' created successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


}
