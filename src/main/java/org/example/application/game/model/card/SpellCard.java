package org.example.application.game.model.card;

public class SpellCard extends Card {
    CardType cardType = CardType.SPELL;

    public SpellCard(String id, String name, float damage, ElementType elementType){
        super(id, name, damage, elementType);
        this.cardType = CardType.SPELL;
    }
}
