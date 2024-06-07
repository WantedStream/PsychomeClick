package com.example.psychomeclick.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The type User data.
 */
public class UserData {
    /**
     * The Name.
     */
    protected String name, /**
     * The Email.
     */
    email;

    /**
     * The Phone.
     */
    protected String phone, /**
     * The User progress.
     */
    userProgress;

    /**
     * The Gson.
     */
    protected Gson gson;

    /**
     * Instantiates a new User data.
     */
    public UserData() {
    }

    /**
     * Instantiates a new User data.
     *
     * @param name            the name
     * @param email           the email
     * @param phone           the phone
     * @param userProgressstr the user progressstr
     */
    public UserData(String name, String email, String phone, String userProgressstr){
        this.name=name;
        this.email=email;

        this.phone=phone;
        this.userProgress=userProgressstr;
        this.gson=new Gson();
    }

    /**
     * Update user progress.
     *
     * @param userProgress the user progress
     */
    public void updateUserProgress(String userProgress){
        this.gson=new Gson();
        this.userProgress=userProgress;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets user progress.
     *
     * @return the user progress
     */
    public String getUserProgress() {
        return userProgress;
    }

    /**
     * Get subject question json array.
     *
     * @param subject the subject
     * @return the json array
     */
    public JsonArray getSubjectQuestion(String subject){
        JsonObject jsonObject = this.gson.fromJson(userProgress, JsonObject.class);
        JsonArray jsonArray=jsonObject.getAsJsonArray(subject);
        return jsonArray;
    }

    /**
     * Get json progress json object.
     *
     * @return the json object
     */
    public JsonObject getJsonProgress(){

        JsonObject jsonObject = this.gson.fromJson(userProgress, JsonObject.class);
        return jsonObject;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user progress.
     *
     * @param userProgress the user progress
     */
    public void setUserProgress(String userProgress) {
        this.userProgress = userProgress;
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", userProgress=" + userProgress +
                '}';
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets gson.
     *
     * @return the gson
     */
    public Gson getGson() {
        return this.gson;
    }
}
