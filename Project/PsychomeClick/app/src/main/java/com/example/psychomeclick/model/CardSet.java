package com.example.psychomeclick.model;

public class CardSet {
    protected String title;
    protected String date;
    protected boolean isPublic;
    protected String description;
    protected String id;
    protected String cards;
    protected String userId;
    public CardSet(String id, String title, String date, boolean isPublic, String description, String cards, String userId) {
        this.title = title;
        this.date = date;
        this.isPublic = isPublic;
        this.description = description;
        this.id = id;
        this.cards = cards;
        this.userId = userId;
    }

    public CardSet() {
        this.title = "";
        this.date = "";
        this.isPublic = false;
        this.description = "";
        this.id = "";
        this.cards = "";
        this.userId = "";
    }
    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCards() {
        return cards;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPublic(boolean isPublic) {this.isPublic=isPublic;}

    public void setDate(String date) {
        this.date= date;
    }

    public void setTitle(String title) {
        this.title= title;
    }

    public void setCards(String cards) {
        this.cards=cards;
    }

    public void setId(String id) {this.id= id;}

    public void setUserId(String userId) {
        this.userId=userId;
    }

    @Override
    public String toString() {
        return "CardSet{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", isPublic=" + isPublic +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", cards='" + cards + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}

