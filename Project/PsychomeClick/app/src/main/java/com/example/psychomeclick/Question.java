package com.example.psychomeclick;

import android.graphics.Bitmap;



public class Question {
    private Bitmap question,answer1,answer2,answer3,answer4;
    private static byte rightAnswer;
    private int questionTag,difficulty;
    private boolean isDapar;

    public Question(Bitmap question, Bitmap answer1, Bitmap answer2, Bitmap answer3, Bitmap answer4, int questionTag, int difficulty, boolean isDapar) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.questionTag = questionTag;
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
                ", id=" + questionTag +
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

    public int getQuestionTag() {
        return questionTag;
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

    public void setQuestionTag(int questionTag) {
        this.questionTag = questionTag;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDapar(boolean dapar) {
        isDapar = dapar;
    }
}
