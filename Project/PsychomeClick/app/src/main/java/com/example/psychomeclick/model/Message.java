package com.example.psychomeclick.model;

/**
 * The type Message.
 */
public class Message {
    /**
     * The Text.
     */
    protected String text;
    /**
     * The Is left.
     */
    protected boolean isLeft;
    /**
     * The Gradually write.
     */
    protected boolean graduallyWrite;
    /**
     * The Icon.
     */
    protected int icon;

    /**
     * Instantiates a new Message.
     *
     * @param text           the text
     * @param isLeft         the is left
     * @param graduallyWrite the gradually write
     * @param icon           the icon
     */
    public Message(String text, boolean isLeft, boolean graduallyWrite, int icon) {
        this.text = text;
        this.isLeft = isLeft;
        this.graduallyWrite = graduallyWrite;
        this.icon = icon;
    }

    /**
     * Instantiates a new Message.
     */
    public Message(){
        this.text = "";
        this.isLeft = false;
        this.graduallyWrite = false;
        this.icon = 0;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Is left boolean.
     *
     * @return the boolean
     */
    public boolean isLeft() {
        return isLeft;
    }


    /**
     * Is gradually write boolean.
     *
     * @return the boolean
     */
    public boolean isGraduallyWrite() {
        return graduallyWrite;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public int getIcon() {
        return icon;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets left.
     *
     * @param left the left
     */
    public void setLeft(boolean left) {
        isLeft = left;
    }

    /**
     * Sets gradually write.
     *
     * @param graduallyWrite the gradually write
     */
    public void setGraduallyWrite(boolean graduallyWrite) {
        this.graduallyWrite = graduallyWrite;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
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