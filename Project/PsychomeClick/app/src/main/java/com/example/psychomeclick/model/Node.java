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

    private Map.Entry[] questionList;

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

    public Map.Entry[] getQuestionList()  {return this.questionList;}
    public int getSubPercents() {
   
        int percent=0;
        if(this.nodes!=null){
            for(Node node:this.nodes){
                int currentpercent=1;
                currentpercent*=node.getSubPercents();
                percent+=currentpercent;
            }
            return percent;
        }

        String progjson= FirebaseManager.userData.getUserProgress().getUserProgressStr();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(progjson, JsonObject.class);
        JsonArray jsonArray=jsonObject.getAsJsonArray(this.name);
        if(jsonArray!=null&&this.getQuestionList().length>0){
            Integer correct=0;
           jsonArray.forEach((e)->{
               JsonArray pairArr=e.getAsJsonArray();
               String id=pairArr.get(0).getAsString();
               int answer=pairArr.get(1).getAsInt();

               FirebaseManager.db.collection("Questions").document(id).get().addOnCompleteListener((t)->{
                   if((int)t.getResult().get("correctAnswer")==answer)

               });
           });

        }
        return (int)(((double)jsonObject1.get("correctAnswers").getAsInt()/this.getQuestionList().length)*100);

        return 0;

    }



}
