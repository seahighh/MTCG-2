package org.example.application.game.model.card;

public class CardHandle {
    private static CardHandle instance;

    private CardHandle(){

    }

    public static CardHandle getInstance(){
        if (CardHandle.instance == null){
            CardHandle.instance = new CardHandle();
        }
        return CardHandle.instance;
    }

    public Card getCard(String id){
        return null;
    }



}