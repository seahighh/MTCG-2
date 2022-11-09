package org.example.application.socialmedia.respository;

import org.example.application.socialmedia.model.Card;

import java.util.List;

public interface PackagesRepository{

    List<Card> findAll();

    Card findByCardId(String id);
    Card save(Card card);
    Card delete(Card card);
}
