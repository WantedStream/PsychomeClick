package com.example.psychomeclick.model;

/**
 * The type Card set.
 */
public class CardSet {
    /**
     * The Title.
     */
    protected String title;
    /**
     * The Date.
     */
    protected String date;
    /**
     * The Is public.
     */
    protected boolean isPublic;
    /**
     * The Description.
     */
    protected String description;
    /**
     * The Id.
     */
    protected String id;
    /**
     * The Cards.
     */
    protected String cards;
    /**
     * The User id.
     */
    protected String userId;

    /**
     * Instantiates a new Card set.
     *
     * @param id          the id
     * @param title       the title
     * @param date        the date
     * @param isPublic    the is public
     * @param description the description
     * @param cards       the cards
     * @param userId      the user id
     */
    public CardSet(String id, String title, String date, boolean isPublic, String description, String cards, String userId) {
        this.title = title;
        this.date = date;
        this.isPublic = isPublic;
        this.description = description;
        this.id = id;
        this.cards = cards;
        this.userId = userId;
    }

    /**
     * Instantiates a new Card set.
     */
    public CardSet() {
        this.title = "";
        this.date = "";
        this.isPublic = false;
        this.description = "";
        this.id = "";
        this.cards = "";
        this.userId = "";
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Is public boolean.
     *
     * @return the boolean
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    public String getCards() {
        return cards;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets is public.
     *
     * @param isPublic the is public
     */
    public void setIsPublic(boolean isPublic) {this.isPublic=isPublic;}

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date= date;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title= title;
    }

    /**
     * Sets cards.
     *
     * @param cards the cards
     */
    public void setCards(String cards) {
        this.cards=cards;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {this.id= id;}

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
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

