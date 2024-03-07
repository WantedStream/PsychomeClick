package com.example.psychomeclick.model;

public class User {
    protected String name,email;
    UserProgress userProgress;

    private String phone;

    public User() {
    }

    public User(String name, String email, String phone, String userProgressstr){
        this.name=name;
        this.email=email;

        this.phone=phone;
        this.userProgress=new UserProgress(userProgressstr);

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserProgress getUserProgress() {
        return userProgress;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserProgress(UserProgress userProgress) {
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
}
