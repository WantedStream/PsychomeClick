package com.example.psychomeclick;

public class User {
    protected String name,email;
    protected UserProgress userProgress;

    private String password;
    private String phone;

    public User(String name,String email,String phone,String password){
        this.name=name;
        this.email=email;
        this.password=password;
        this.phone=phone;
        this.userProgress=new UserProgress();

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

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userProgress=" + userProgress +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
