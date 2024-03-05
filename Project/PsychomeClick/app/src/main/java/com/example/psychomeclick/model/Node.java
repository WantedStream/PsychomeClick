package com.example.psychomeclick.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Node {
    private String name;
    private Node[] nodes;
    private String[] questionList; // Assuming this is also required

    private static void listNodes(Node node) {
        System.out.println("Node Name: " + node.getName());
        if (node.getNodes() != null) {
            for (Node subNode : node.getNodes()) {
                listNodes(subNode);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public String[] getQuestionList() {
        return questionList;
    }
}
