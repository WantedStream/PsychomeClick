package com.example.psychomeclick.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class Node {
    private String name;
    private Node[] nodes;

    private String[] questionList;
    private static void listNodes(Node node) {
        System.out.println("Node Name: " + node.getName());
        if (node.getNodes() != null) {
            for (Node subNode : node.getNodes()) {
                listNodes(subNode);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public Node[] getNodes() {
        return this.nodes;
    }

    public String[] getQuestionList()  {return this.questionList;}
    public int getSubPercents() {
        int percent=1;
        for(Node node:this.nodes){
            int currentpercent=1;
            currentpercent*=node.getSubPercents();
            percent+=currentpercent;
        }
        return percent;
    }

}
