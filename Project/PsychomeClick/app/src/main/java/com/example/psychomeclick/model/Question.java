package com.example.psychomeclick.model;

import android.graphics.Bitmap;



public class Question {
    private Bitmap question,answer1,answer2,answer3,answer4;
    private byte rightAnswer;
    private  int difficulty;
    private String questionTag;
    public Question(Bitmap question, Bitmap answer1, Bitmap answer2, Bitmap answer3, Bitmap answer4, String questionTag, byte rightAnswer,int difficulty) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.rightAnswer=rightAnswer;
        this.questionTag = questionTag;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question=" + question +
                ", answer1=" + answer1 +
                ", answer2=" + answer2 +
                ", answer3=" + answer3 +
                ", answer4=" + answer4 +
                ", id=" + questionTag +
                ", difficulty=" + difficulty +
                '}';
    }


    public Bitmap getQuestion() {
        return question;
    }

    public Bitmap getAnswer1() {
        return answer1;
    }

    public Bitmap getAnswer2() {
        return answer2;
    }

    public Bitmap getAnswer3() {
        return answer3;
    }

    public Bitmap getAnswer4() {
        return answer4;
    }

    public byte getRightAnswer() {
        return this.rightAnswer;
    }

    public String getQuestionTag() {
        return questionTag;
    }

    public int getDifficulty() {
        return difficulty;
    }


    public void setQuestion(Bitmap question) {
        this.question = question;
    }

    public void setAnswer1(Bitmap answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(Bitmap answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(Bitmap answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(Bitmap answer4) {
        this.answer4 = answer4;
    }

    public void setRightAnswer(byte rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setQuestionTag(String questionTag) {
        this.questionTag = questionTag;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
