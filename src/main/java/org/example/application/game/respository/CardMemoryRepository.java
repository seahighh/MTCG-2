package org.example.application.game.respository;

import org.example.application.game.model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMemoryRepository implements CardRepository {

    private final List<Card> Cards;


    public CardMemoryRepository() {
        this.Cards = new ArrayList<>();
    }

    public List<Card> findAll(){
        return this.Cards;
    }

    @Override
    public Card findByCardId(String id) {
        for (Card card: this.Cards){
            if (card.getId().equals(id)){
                return card;
            }
        }
        return null;
    }

    @Override
    public Card save(Card card) {
        if (!this.Cards.contains(card)){
            this.Cards.add(card);
        }
        return null;
    }

    @Override
    public Card delete(Card card) {
        if (this.Cards.contains(card)){
            this.Cards.remove(card);
        }
        return null;
    }
}
