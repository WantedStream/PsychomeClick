package com.example.psychomeclick.model;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private String name;
    private Node[] nodes;

    private String[] questionList;

    public int percent;
    private static void listNodes(Node node) {
        System.out.println("Node Name: " + node.getName());
        if (node.getNodes() != null) {
            for (Node subNode : node.getNodes()) {
                listNodes(subNode);
            }
        }
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getName() {
        return this.name;
    }

    public Node[] getNodes() {
        return this.nodes;
    }

    public String[] getQuestionList()  {return this.questionList;}
    public int getSubPercents() {
   
        int percent=0;
        if(this.nodes!=null){
            for(Node node:this.nodes){
                int currentpercent=1;
                currentpercent*=node.getSubPercents()/ this.nodes.length;
                percent+=currentpercent;
            }
            return percent;
        }

        JsonArray jsonArray=FirebaseManager.userData.getSubjectQuestion(this.name);
        if(jsonArray!=null&&this.getQuestionList().length>0){
            Integer correct=0;
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonArray pairArr=jsonArray.get(i).getAsJsonArray();
                String id=pairArr.get(0).getAsString();
                int answer=pairArr.get(1).getAsInt();
                Integer question=FirebaseManager.QuestionMap.get(id);
                if(question!=null&&question.equals(answer))
                    correct++;
            }
            return (int)(((double)correct/this.getQuestionList().length)*100);

        }

        return 0;

    }



}
