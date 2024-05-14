package com.example.psychomeclick.model;
import com.google.gson.JsonArray;

import java.util.Arrays;

public class Node {
    protected String name;
    protected Node[] nodes;

    protected String[] questionList;

    protected int percent;

    public Node(String name, Node[] nodes, String[] questionList, int percent) {
        this.name = name;
        this.nodes = nodes;
        this.questionList = questionList;
        this.percent = percent;
    }
    public Node() {
        this.name = "";
        this.nodes = null;
        this.questionList = null;
        this.percent = 0;
    }
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


    public void setName(String name) {
        this.name = name;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public void setQuestionList(String[] questionList) {
        this.questionList = questionList;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", nodes=" + Arrays.toString(nodes) +
                ", questionList=" + Arrays.toString(questionList) +
                ", percent=" + percent +
                '}';
    }
}
