package com.example.psychomeclick.model;

/**
 * The type Card.
 */
public class Card {
    /**
     * The Term.
     */
    protected String term;
    /**
     * The Meaning.
     */
    protected String meaning;

    /**
     * Instantiates a new Card.
     *
     * @param term    the term
     * @param meaning the meaning
     */
    public Card(String term, String meaning) {
        this.term = term;
        this.meaning = meaning;
    }

    /**
     * Instantiates a new Card.
     */
    public Card() {
        this.term = "";
        this.meaning = "";
    }

    /**
     * Gets term.
     *
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Gets meaning.
     *
     * @return the meaning
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Sets term.
     *
     * @param term the term
     */
    public void setTerm(String term) {this.term=term;}

    /**
     * Sets meaning.
     *
     * @param meaning the meaning
     */
    public void setMeaning(String meaning) {this.meaning=meaning;}
}
