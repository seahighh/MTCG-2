package org.example.application.game.respository;

import org.example.application.game.model.card.Card;
import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;

import java.util.List;

public interface CardRepository {

    List<Card> findAll();
    List<Card> getCardsForUser(User user);
    List<Card> getCardsForPackage(Package packages);
    Card findByCardId(String id);
    Card findByUserAndCCardId(User user, String id);
    Card save(Card card);
    Card addCardToUser(Card card, User user);
    Card addCardToPackage(Card card, int p_id);

}
