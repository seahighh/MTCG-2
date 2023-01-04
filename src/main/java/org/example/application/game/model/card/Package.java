package org.example.application.game.model.card;

import lombok.Builder;

import java.util.List;

@Builder
public class Package {

    private int id;
    private int price = 5;
    private int legal = 1;

    public Package(int id, int price, int legal, List<Card> cards) {
        this.id = id;
        this.price = price;
        Cards = cards;
        this.legal = legal;
    }

    private List<Card> Cards;

    public int getPrice() {
        return price;
    }


    public Package() {
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
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

    public int getLegal() {
        return legal;
    }

    public void setLegal(int legal) {
        this.legal = legal;
    }
}

