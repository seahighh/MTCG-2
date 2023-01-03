package org.example.application.game.model.card;


public enum CardType {
    MONSTER("Monster"),

    SPELL("Spell");

    public String message;
    CardType(String message){
        this.message = message;
    }
}
