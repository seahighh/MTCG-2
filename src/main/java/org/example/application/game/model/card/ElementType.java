package org.example.application.game.model.card;


public enum ElementType {

    FIRE("Fire"),

    WATER("Water"),

    NORMAL("Normal");

    public String message;

    ElementType(String message){
        this.message = message;
    }
}
