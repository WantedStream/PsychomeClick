package com.example.psychomeclick.model;

import android.graphics.Bitmap;

import java.util.List;


public class Question {
    private List<Bitmap> images;
    private byte rightAnswer;
    private  int difficulty;
    private String questionTag;
    public Question(List<Bitmap> images, String questionTag, byte rightAnswer,int difficulty) {
        this.images = images;
        this.rightAnswer=rightAnswer;
        this.questionTag = questionTag;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question=" + this.images.get(0) +
                ", answer1=" + this.images.get(1) +
                ", answer2=" + this.images.get(2) +
                ", answer3=" +this.images.get(3) +
                ", answer4=" + this.images.get(4) +
                ", id=" + questionTag +
                ", difficulty=" + difficulty +
                '}';
    }


    public List<Bitmap> getImages() {
        return this.images;
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


    public void setImages(List<Bitmap> bitmapList) {
        this.images = bitmapList;
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
