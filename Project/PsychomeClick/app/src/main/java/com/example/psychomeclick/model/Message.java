package com.example.psychomeclick.model;
import android.graphics.Bitmap;

public class Message {
    protected String text;
    protected boolean isLeft;
    protected boolean graduallyWrite;
    protected int icon;

    public Message(String text, boolean isLeft, boolean graduallyWrite, int icon) {
        this.text = text;
        this.isLeft = isLeft;
        this.graduallyWrite = graduallyWrite;
        this.icon = icon;
    }
    public Message(){
        this.text = "";
        this.isLeft = false;
        this.graduallyWrite = false;
        this.icon = 0;
    }
    public String getText() {
        return text;
    }

    public boolean isLeft() {
        return isLeft;
    }


    public boolean isGraduallyWrite() {
        return graduallyWrite;
    }

    public int getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public void setGraduallyWrite(boolean graduallyWrite) {
        this.graduallyWrite = graduallyWrite;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", isLeft=" + isLeft +
                ", graduallyWrite=" + graduallyWrite +
                ", icon=" + icon +
                '}';
    }
}