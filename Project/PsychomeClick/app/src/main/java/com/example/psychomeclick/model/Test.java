package com.example.psychomeclick.model;

import java.util.List;

public class Test {
    protected List<Question> questionList;
    protected int time;
    protected short points;

    public Test(List<Question> questionList, int time, short points) {
        this.questionList = questionList;
        this.time = time;
        this.points = points;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Test{" +
                "questionList=" + questionList +
                ", time=" + time +
                ", points=" + points +
                '}';
    }
}
