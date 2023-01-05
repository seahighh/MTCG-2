package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeckMemoryRepository {
    private static DeckMemoryRepository instance;

    private CardMemoryRepository cardMemoryRepository;

    public DeckMemoryRepository(){

        cardMemoryRepository = CardMemoryRepository.getInstance();
    }

    public  DeckMemoryRepository getInstance() {
        if (DeckMemoryRepository.instance == null) {
            DeckMemoryRepository.instance = new DeckMemoryRepository();
        }
        return DeckMemoryRepository.instance;
    }

    public List<Card> getDeck(User user){

        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id = ? AND in_deck;");

            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            List<Card> cards = new ArrayList<>();
            while(rs.next()){
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(rs.getString("card_type"));
                card.setElementType(rs.getString("element_type"));
                card.setLocked(rs.getBoolean("is_locked"));
                cards.add(card);
            }
            rs.close();
            ps.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addCardsWithIdsToDeck(String[] ids, User user){
        List<Card> userCards;
        userCards = cardMemoryRepository.getCardsForUser(user);
        List<Card> newDeck = new ArrayList<>();

        if (ids.length == 4) {
            for (int i = 0; i < (ids.length); i++) {
                // Check if the card belongs to the user
                Card card;
                card = cardMemoryRepository.findByUserAndCCardId(user, ids[i]);
                if (card!=null){
                    newDeck.add(card);
                }

            }
            // Only when all cards belong to the user
            if (newDeck.size() == 4) {
                // Clear the deck
                this.clearDeck(user);

                // Attach new cards to the deck
                for (Card card : newDeck) {
                    this.addCardToDeck(card, user);
                }
                return true;
            }
        }

        // Otherwise return false
        return false;

    }
    public boolean addCardToDeck(Card card, User user) {
        if (card.isLocked()) {
            return false;
        }

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(id) FROM  cards WHERE user_id = ? AND in_deck;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                // A deck consists of a maximum of 5 cards.
                if (count >= 4) {
                    return false;
                }

                ps = conn.prepareStatement("UPDATE cards SET in_deck = TRUE, user_id = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setString(2, card.getId());

                int affectedRows = ps.executeUpdate();

                rs.close();
                ps.close();
                conn.close();

                return affectedRows != 0;
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean clearDeck(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET in_deck = FALSE WHERE user_id = ?;");
            ps.setInt(1, user.getId());

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
