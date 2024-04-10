package com.example.psychomeclick.model;

public class CardSet {
    private String title;
    private String date;
    private boolean isPublic;
    private String description;
    private String id;
    private String Cards;
    private String userId;
    public CardSet(String id, String title, String date, boolean isPublic, String description, String cards, String userId) {
        this.title = title;
        this.date = date;
        this.isPublic = isPublic;
        this.description = description;
        this.id = id;
        this.Cards = cards;
        this.userId = userId;
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
        return Cards;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
