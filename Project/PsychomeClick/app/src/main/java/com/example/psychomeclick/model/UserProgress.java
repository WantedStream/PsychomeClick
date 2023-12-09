package com.example.psychomeclick.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserProgress{

    private JsonReader jr;

    public UserProgress(String currentProgress) {
        this.currentProgress = currentProgress;
    }

    private String currentProgress;

    public <T,V> void addToPath(Context context, Map.Entry<T,V> subjectEntry, String[] paths) {
            //find
        File jsonFile = new File(context.getFilesDir(), "progress_folder/user_progress.json");
        JsonNode[] jsNodes =  navigateTo( context,paths,false);
       // ((ObjectNode) jsNodes[1]).put((String)subjectEntry.getKey(), new JsonNode(subjectEntry.getValue());
    }

    public void createSave(Context context) {
        AssetManager assetManager= context.getAssets();

    Map<String, Object> data = new HashMap<>();
        data.put("Quantitative reasoning",new HashMap<>());
        HashMap VerbalReasoning= new HashMap<>();
        VerbalReasoning.put("Analogies",new HashMap<>());

        data.put("Verbal reasoning", VerbalReasoning);
        data.put("English", new HashMap<>());


        ObjectMapper objectMapper = new ObjectMapper();

        File internalDir = new File(context.getFilesDir(), "progress_folder");
        //internalDir.mkdirs();

        File file = new File(internalDir, "user_progress.json");

        try {
        // Write the Map to a JSON file
        objectMapper.writeValue(file, data);
        System.out.println("JSON file '" + file.getName() + "' created successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }


    }
    public JsonNode[] navigateTo(Context context, String[] paths, boolean printPath){

        ObjectMapper objectMapper = new ObjectMapper();
        // Specify the file path
        File jsonFile = new File(context.getFilesDir(), "progress_folder/user_progress.json");
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(fileToString(jsonFile));
            final JsonNode[] toRet=new JsonNode[2];
            toRet[0]=jsonNode;
            return navigateToRec(jsonNode, paths,0,printPath,toRet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    private JsonNode[] navigateToRec( JsonNode jsonNode,String[] paths,int index,boolean printPath,final JsonNode[] toRet){
        ;//ליצור מערך פינלי כי לא נותן למשתנה רגיל של JsonNode

        if (jsonNode.isObject()) {
            jsonNode.fields().forEachRemaining(entry -> {
               // System.out.println(entry.getKey() +" equals? = "+paths[index]);
                if (entry.getKey()==paths[index]){
                         if(printPath)
                             System.out.println("->"+ entry.getKey());
                    //System.out.println(index+"<"+(paths.length-1));

                    if(index<paths.length-1)
                        toRet[1] = navigateToRec(entry.getValue() ,paths, index+1,printPath,toRet)[1];
                    else{
                        toRet[1] =jsonNode;
                        return;
                    }


                }
            });
            if(toRet[1]!=null){
                return toRet;
            }
            throw new RuntimeException("invalid json path");
        }
            return null;
    }

    public void printExistingJsonTree(Context context){
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            File jsonFile = new File(context.getFilesDir(), "progress_folder/user_progress.json");
            System.out.println("creating Json tree");

            // Read the JSON string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(fileToString(jsonFile));

            // Print the JSON tree
            System.out.println("JSON Tree:");
            printJsonTree(jsonNode, 0);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    private String fileToString(File file){
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static void printJsonTree(JsonNode jsonNode, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }

        if (jsonNode.isObject()) {
            System.out.println("Object {");
            jsonNode.fields().forEachRemaining(entry -> {
                for (int i = 0; i <= indent; i++) {
                    System.out.print("  ");
                }
                System.out.println(entry.getKey() + ":");
                printJsonTree(entry.getValue(), indent + 1);
            });
            for (int i = 0; i < indent; i++) {
                System.out.print("  ");
            }
            System.out.println("}");
        } else if (jsonNode.isArray()) {
            System.out.println("Array [");
            for (JsonNode element : jsonNode) {
                printJsonTree(element, indent + 1);
            }
            for (int i = 0; i < indent; i++) {
                System.out.print("  ");
            }
            System.out.println("]");
        } else {
            System.out.println(jsonNode.asText());
        }
    }

}
