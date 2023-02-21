package org.example.application.game.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void winsAgainst2() {
        Card card = new Card();
        card.setName("Wizard");
        card.setCardType("Wizard");

        Card card1 = new Card();
        card1.setName("Ork");
        card1.setCardType("Ork");

        Assertions.assertEquals(true,card.winsAgainst(card1));
    }

    @Test
    void calculateDamage2() {
        Card card1 = new Card();
        card1.setCardType("WaterSpell");
        card1.setElementType("WaterSpell");
        card1.setDamage(20);

        Card card2 = new Card();
        card2.setCardType("NormalSpell");
        card2.setElementType("NormalSpell");
        card2.setDamage(15);

        assertEquals(30,card2.calculateDamage(card1));
        assertEquals(10,card1.calculateDamage(card2));
    }

    @Test
    void winsAgainst3() {
        Card card = new Card();
        card.setName("FireElf");
        card.setCardType("FireElf");

        Card card1 = new Card();
        card1.setName("Dragon");
        card1.setCardType("Dragon");

        Assertions.assertEquals(true,card.winsAgainst(card1));
    }

    @Test
    void calculateDamage3() {
        Card card1 = new Card();
        card1.setCardType("NormalSpell");
        card1.setElementType("NormalSpell");
        card1.setDamage(20);

        Card card2 = new Card();
        card2.setCardType("FireSpell");
        card2.setElementType("FireSpell");
        card2.setDamage(15);

        assertEquals(30,card2.calculateDamage(card1));
        assertEquals(10,card1.calculateDamage(card2));
    }

}