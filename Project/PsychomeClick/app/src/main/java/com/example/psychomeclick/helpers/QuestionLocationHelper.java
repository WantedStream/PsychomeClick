package com.example.psychomeclick.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class QuestionLocationHelper {

        public static String findQuestionLocation(String questionId, String jsonString) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            return findQuestionLocationHelper(jsonObject, questionId);
        }

    private static String findQuestionLocationHelper(JsonObject jsonObject, String questionId) {
        if (jsonObject.has("questionList")) {
            JsonArray questionList = jsonObject.getAsJsonArray("questionList");
            for (int i = 0; i < questionList.size(); i++) {
                String currentId = questionList.get(i).getAsString();
                if (currentId.equals(questionId)) {
                    // Found in current list, return the current object's name (subject)
                    return jsonObject.get("name").getAsString();
                }
            }
        }

        if (jsonObject.has("nodes")) {
            JsonArray nodes = jsonObject.getAsJsonArray("nodes");
            for (int i = 0; i < nodes.size(); i++) {
                JsonObject childNode = nodes.get(i).getAsJsonObject();
                String location = findQuestionLocationHelper(childNode, questionId);
                if (location != null) return location;
            }
        }

        return null; // Question not found
    }

    public static String ChangeQuestionLocation(String questionId, String jsonString,String newLcation) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        removeQuestionFromTree(jsonObject,questionId);
        addQuestionToSubject(jsonObject,questionId,newLcation);
        return jsonObject.toString();
    }

    public static String AddQuestionLocation(String questionId, String jsonString,String newLcation) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        addQuestionToSubject(jsonObject,questionId,newLcation);
        return jsonObject.toString();
    }
    public static JsonObject removeQuestionFromTree(JsonObject root, String questionId) {
        if (root.has("questionList")) {
            JsonArray questionList = root.getAsJsonArray("questionList");
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getAsString().equals(questionId)) {
                    questionList.remove(i);
                    return root; // Early return after removal
                }
            }
            return root;
        }

        JsonArray nodes = root.getAsJsonArray("nodes");
        for (int i = 0; i < nodes.size(); i++) {
          removeQuestionFromTree(nodes.get(i).getAsJsonObject(), questionId);
        }

        return root;
    }

    private static JsonObject addQuestionToSubject(JsonObject root, String questionId, String subjectName) {
        if (root.get("name").getAsString().equals(subjectName)) {
            root.getAsJsonArray("questionList").add(new Gson().toJsonTree(questionId));
            return root;
        }
        if(root.has("nodes")){
            JsonArray nodes = root.getAsJsonArray("nodes");
            for (int i = 0; i < nodes.size(); i++)
                addQuestionToSubject(nodes.get(i).getAsJsonObject(), questionId, subjectName);
        }


        return root;
    }
}
