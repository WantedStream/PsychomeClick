package com.example.psychomeclick;

public class LastSubject extends Subject{
    private int percent=0;
    @Override
    public int getSubPercents() {
        return this.percent;
    }


    public void openQuestion(UserProgress userProgress) {
        //int questionId=userProgress.navigateToQuestion(subjectName);

    }
}
