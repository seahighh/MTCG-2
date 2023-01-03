package org.example.application.game.model.card;

public class Card{
    private String id;
    private String name;
    private float damage;
    private String elementType;
    private String cardType;

    private boolean isLocked;

    public Card(String id, String name, float damage, String elementType){
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }

    public static Card info(String id, String name, float damage, String card_type, String element_type, boolean isLocked){
        ElementType elementType;
        CardType cardType;
        Card card;

        cardType = CardType.valueOf(card_type);
        elementType = ElementType.valueOf(element_type);

        if (CardType.MONSTER.equals(cardType)){
            card = MonsterCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(String.valueOf(elementType))
                    .build();
        }
        else {
            card = SpellCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(String.valueOf(elementType))
                    .build();
        }

        return card;
    }

    public boolean isLocked() {
        return isLocked;
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

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String name) {
        if (name.contains("Water")){
            this.elementType = ElementType.WATER.message;
        } else if (name.contains("Fire")) {
            this.elementType = ElementType.FIRE.message;
        } else if (name.contains("Normal")) {
            this.elementType = ElementType.NORMAL.message;
        }
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String name) {
        if (name.contains("Monster")) {
            this.cardType = CardType.MONSTER.message;
        }else {
            this.cardType = CardType.SPELL.message;
        }
    }
}
