package com.example.psychomeclick.model;

public class Card {
    private String term;
    private String meaning;
    public Card(String term, String meaning) {
        this.term = term;
        this.meaning = meaning;
    }

    public String getTerm() {
        return term;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setTerm(String term) {this.term=term;}
    public void setMeaning(String meaning) {this.meaning=meaning;}
}
