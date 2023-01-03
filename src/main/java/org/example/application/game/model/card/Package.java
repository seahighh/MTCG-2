package org.example.application.game.model.card;

import lombok.Builder;

import java.util.List;

@Builder
public class Package {

    private int id;
    private int price = 5;

    public Package(int id, List<Card> Cards, int price) {
        this.id = id;
        this.Cards = Cards;
        this.price = price;
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







}

