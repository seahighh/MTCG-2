package org.example.application.socialmedia.model;

public class Card{
    private String id;
    private String name;
    private float damage;
    private String elementType;

    public Card(){

    }

    public Card(String id, String name, float damage){
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getDamage() {
        return damage;
    }


    public void setDamage(float damage) {
        this.damage = damage;
    }


    public String getElementType() {
        return elementType;
    }


    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

}
