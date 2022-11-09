package org.example.application.socialmedia.model;

public class Card {
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

    /**
     * 获取
     * @return damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 设置
     * @param damage
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * 获取
     * @return elementType
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * 设置
     * @param elementType
     */
    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String toString() {
        return "Card{id = " + id + ", name = " + name + ", damage = " + damage + ", elementType = " + elementType + "}";
    }
}
