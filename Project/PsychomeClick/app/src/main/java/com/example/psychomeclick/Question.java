package com.example.psychomeclick;

import android.graphics.Bitmap;



public class Question {
    private Bitmap question,answer1,answer2,answer3,answer4;
    private static byte rightAnswer;
    private int id,difficulty;
    private boolean isDapar;

    public Question(Bitmap question, Bitmap answer1, Bitmap answer2, Bitmap answer3, Bitmap answer4, int id, int difficulty, boolean isDapar) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.id = id;
        this.difficulty = difficulty;
        this.isDapar = isDapar;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question=" + question +
                ", answer1=" + answer1 +
                ", answer2=" + answer2 +
                ", answer3=" + answer3 +
                ", answer4=" + answer4 +
                ", id=" + id +
                ", difficulty=" + difficulty +
                ", isDapar=" + isDapar +
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

    public static byte getRightAnswer() {
        return rightAnswer;
    }

    public int getId() {
        return id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isDapar() {
        return isDapar;
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

    public static void setRightAnswer(byte rightAnswer) {
        Question.rightAnswer = rightAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDapar(boolean dapar) {
        isDapar = dapar;
    }
}
