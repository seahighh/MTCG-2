package org.example.application.game.model.card;

import lombok.Builder;

public class SpellCard extends Card {
    CardType cardType = CardType.SPELL;

    @Builder
    public SpellCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType = CardType.SPELL;
    }
}
