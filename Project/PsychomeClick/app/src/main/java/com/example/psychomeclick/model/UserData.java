package com.example.psychomeclick.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UserData {
    protected String name,email;

    protected String phone,userProgress;

    protected Gson gson;

    public UserData() {
    }

    public UserData(String name, String email, String phone, String userProgressstr){
        this.name=name;
        this.email=email;

        this.phone=phone;
        this.userProgress=userProgressstr;
        this.gson=new Gson();
    }
    public void updateUserProgress(String userProgress){
        this.gson=new Gson();
        this.userProgress=userProgress;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserProgress() {
        return userProgress;
    }

    public JsonArray getSubjectQuestion(String subject){
        JsonObject jsonObject = this.gson.fromJson(userProgress, JsonObject.class);
        JsonArray jsonArray=jsonObject.getAsJsonArray(subject);
        return jsonArray;
    }

    public JsonObject getJsonProgress(){

        JsonObject jsonObject = this.gson.fromJson(userProgress, JsonObject.class);
        return jsonObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gson getGson() {
        return this.gson;
    }
}
