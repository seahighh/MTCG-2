package org.example.application.game.model.card;

public class Card{
    private String id;
    private String name;
    private float damage;
    ElementType elementType;

    CardType cardType;

    public Card(String id, String name, float damage, ElementType elementType){
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
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

    public static Card info(String id, String name, float damage, String card_type, String element_type){
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
                    .elementType(elementType)
                    .build();
        }
        else {
            card = SpellCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(elementType)
                    .build();
        }

        return card;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public ElementType getElementType(){
        return elementType;
    }

    public CardType getCardType(){
        return cardType;
    }


//    public String getElementType() {
//        return elementType;
//    }
//
//
//    public void setElementType(String elementType) {
//        this.elementType = elementType;
//    }
//
}
