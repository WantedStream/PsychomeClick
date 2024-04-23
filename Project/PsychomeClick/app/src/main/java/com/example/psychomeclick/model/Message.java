package com.example.psychomeclick.model;
import android.graphics.Bitmap;

public class Message {
    private String text;
    private boolean isLeft;
    private int color;
    private boolean graduallyWrite;
    private int icon;

    public Message(String text, boolean isLeft, int color, boolean graduallyWrite, int icon) {
        this.text = text;
        this.isLeft = isLeft;
        this.color = color;
        this.graduallyWrite = graduallyWrite;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public int getColor() {
        return color;
    }

    public boolean isGraduallyWrite() {
        return graduallyWrite;
    }

    public int getIcon() {
        return icon;
    }
}