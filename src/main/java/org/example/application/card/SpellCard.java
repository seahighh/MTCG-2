package org.example.application.card;

import org.example.application.socialmedia.model.Card;

public class SpellCard extends Card {

    CardType cardType = CardType.SPELL;

    public SpellCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType = CardType.SPELL;
    }
}
