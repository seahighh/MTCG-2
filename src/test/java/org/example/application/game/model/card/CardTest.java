package org.example.application.game.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void winsAgainst() {
        Card card = new Card();
        card.setName("WaterGoblin");
        card.setCardType("WaterGoblin");

        Card card1 = new Card();
        card1.setName("Dragon");
        card1.setCardType("Dragon");

        Assertions.assertEquals(true,card1.winsAgainst(card));
    }

    @Test
    void calculateDamage() {
        Card card1 = new Card();
        card1.setCardType("FireSpell");
        card1.setElementType("FireSpell");
        card1.setDamage(20);

        Card card2 = new Card();
        card2.setCardType("WaterSpell");
        card2.setElementType("WaterSpell");
        card2.setDamage(15);

        assertEquals(30,card2.calculateDamage(card1));
        assertEquals(10,card1.calculateDamage(card2));
    }
}