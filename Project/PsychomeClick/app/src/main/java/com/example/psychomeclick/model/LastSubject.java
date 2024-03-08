package com.example.psychomeclick.model;

public class LastSubject extends Subject{
    private int percent=0;
    @Override
    public int getSubPercents() {
        return this.percent;
    }


    public void openQuestion(JsFunctions userProgress) {
        //int questionId=userProgress.navigateToQuestion(subjectName);

    }
}
