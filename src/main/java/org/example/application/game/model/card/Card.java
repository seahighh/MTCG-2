package org.example.application.game.model.card;

public class Card{
    private String Id;
    private String Name;
    private float Damage;
    private String elementType;
    private String cardType;

    private boolean isLocked;
    public Card(){}

    public Card(String id, String name, float damage, String elementType){
        this.Id = id;
        this.Name = name;
        this.Damage = damage;
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
        return Id;
    }


    public void setId(String id) {
        this.Id = id;
    }


    public String getName() {
        return Name;
    }


    public void setName(String name) {
        this.Name = name;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public float getDamage() {
        return Damage;
    }

    public void setDamage(float damage) {
        this.Damage = damage;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String name) {
        if (name.contains("Water")){
            this.elementType = ElementType.WATER.message;
        } else if (name.contains("Fire")) {
            this.elementType = ElementType.FIRE.message;
        } else {
            this.elementType = ElementType.NORMAL.message;
        }
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String name) {
        if (name.contains("Spell")) {
            this.cardType = CardType.SPELL.message;
        }else {
            this.cardType = CardType.MONSTER.message;
        }
    }
}
