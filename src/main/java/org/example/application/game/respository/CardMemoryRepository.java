package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardMemoryRepository implements CardRepository {

    private  List<Card> Cards;


    public CardMemoryRepository() {
        this.Cards = new ArrayList<>();
    }

    public List<Card> findAll(){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type FROM cards");


            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();

            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)));

            }
            rs.close();
            ps.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Card findByCardId(String id) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE id=?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Card card = Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                );
                rs.close();
                ps.close();
                conn.close();

                return card;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Card findByUserAndCCardId(User user, String id) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id=? AND id = ? ;");
            ps.setInt(1, user.getId());
            ps.setString(2, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Card card = Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)
                );
                rs.close();
                ps.close();
                conn.close();

                return card;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Card save(Card card) {
        card.setCardType(card.getName());
        card.setElementType(card.getName());
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(id, name, damage, element_type, card_type) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, card.getId());
            ps.setString(2, card.getName());
            ps.setFloat(3, card.getDamage());
            ps.setString(4, card.getElementType());
            ps.setString(5, card.getCardType());

            ps.execute();
            conn.close();
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Card> getCardsForUser(User user){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id = ?;");
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)

                ));
            }

            rs.close();
            ps.close();
            conn.close();
            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Card> getCardsForPackage(Package packages){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE package_id = ?;");
            ps.setInt(1, packages.getId());

            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6)

                ));
            }

            rs.close();
            ps.close();
            conn.close();
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Card addCardToPackage(Card card, int pid){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = ? WHERE id = ?;");
            ps.setInt(1, pid);
            ps.setString(2, card.getId());

            ps.close();
            conn.close();

            return this.findByCardId(card.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Card addCardToUser(Card card, User user){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = NULL, user_id = ? WHERE id = ?;");
            ps.setInt(1, user.getId());
            ps.setString(2, card.getId());

            ps.close();
            conn.close();
            return this.findByCardId(card.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
