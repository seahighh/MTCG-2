package org.example.application.game.model.card;

public class MonsterCard extends Card {
    CardType cardType =CardType.MONSTER;

    public MonsterCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType =CardType.MONSTER;

    }
}
