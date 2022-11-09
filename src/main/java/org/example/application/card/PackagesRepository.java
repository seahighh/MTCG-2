package org.example.application.card;

import java.util.List;

public interface PackagesRepository{

    List<Card> findAll();

    Card findByCardId(String id);
    Card save(Card card);
    Card delete(Card card);
}
