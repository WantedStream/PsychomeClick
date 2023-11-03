package com.projectclasses.prototypeclasses;

import java.awt.image.BufferedImage;

public class Question {
    private BufferedImage question,answer1,answer2,answer3,answer4;
    private static byte rightAnswer;
    private int id,difficulty;
    private boolean isDapar;

    public Question(BufferedImage question, BufferedImage answer1, BufferedImage answer2, BufferedImage answer3, BufferedImage answer4, int id, int difficulty, boolean isDapar) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.id = id;
        this.difficulty = difficulty;
        this.isDapar = isDapar;
    }

    public BufferedImage getQuestion() {
        return question;
    }

    public BufferedImage getAnswer1() {
        return answer1;
    }

    public BufferedImage getAnswer2() {
        return answer2;
    }

    public BufferedImage getAnswer3() {
        return answer3;
    }

    public BufferedImage getAnswer4() {
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

    public void setQuestion(BufferedImage question) {
        this.question = question;
    }

    public void setAnswer1(BufferedImage answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(BufferedImage answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(BufferedImage answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(BufferedImage answer4) {
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
