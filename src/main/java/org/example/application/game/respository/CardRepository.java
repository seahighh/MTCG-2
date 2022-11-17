package org.example.application.game.respository;

import org.example.application.game.model.card.Card;

import java.util.List;

public interface CardRepository {

    List<Card> findAll();

    Card findByCardId(String id);
    Card save(Card card);
    Card delete(Card card);
}
