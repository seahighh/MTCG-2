package org.example.application.game.model.card;

import java.util.List;

public class Package {
    private String id;

    private List<Card> Cards;

    private String name;


    public Package() {
    }

    public Package(String id, List<Card> Cards, String name) {
        this.id = id;
        this.Cards = Cards;
        this.name = name;
    }

    /**
     * 获取
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return Cards
     */
    public List<Card> getCards() {
        return Cards;
    }

    /**
     * 设置
     * @param Cards
     */
    public void setCards(List<Card> Cards) {
        this.Cards = Cards;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Package{id = " + id + ", Cards = " + Cards + ", name = " + name + "}";
    }
}

