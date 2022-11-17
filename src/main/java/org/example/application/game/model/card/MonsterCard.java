package org.example.application.game.model.card;

import lombok.Builder;

public class MonsterCard extends Card {
    CardType cardType =CardType.MONSTER;

    @Builder
    public MonsterCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType =CardType.MONSTER;

    }
}
