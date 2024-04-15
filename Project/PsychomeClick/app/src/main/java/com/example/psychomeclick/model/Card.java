package com.example.psychomeclick.model;

public class Card {
    private String term;
    private String meaning;
    private String imageId;

    public Card(String term, String meaning, String imageId) {
        this.term = term;
        this.meaning = meaning;
        this.imageId = imageId;
    }

    public String getTerm() {
        return term;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getImageId() {
        return imageId;
    }
}
