package org.example.application.card;

import org.example.application.socialmedia.model.Card;

public class MonsterCard extends Card {

    CardType cardType =CardType.MONSTER;

    public MonsterCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType =CardType.MONSTER;

    }
}
