package org.example.application.game.respository;

import org.example.Database.Database;
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
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, name, damage, card_type, element_type FROM cards");

            List<Card> cards = new ArrayList<>();

            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5)
                ));

            }
            rs.close();
            sm.close();
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
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type FROM cards WHERE id=?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Card card = Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5));
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
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(name, damage, element_type, card_type, package_id, user_name) VALUES (?. ?. ?. ?. ?. ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, card.getName());
            ps.setFloat(2, card.getDamage());
            ps.setString(3, card.getElementType().toString());
            ps.setString(4, card.getCardType().toString());
            ps.setNull(5, Types.NULL);
            ps.setNull(6, Types.NULL);

            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }

    public List<Card> getCardsForUser(User user){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type FROM cards WHERE user_name = ?;");
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5)

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
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type FROM cards WHERE package_id = ?;");
            ps.setString(1, packages.getId());

            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()){
                cards.add(Card.info(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5)

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

    public Card addCardToPackage(Card card, Package packages){
        Connection conn = Database.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE cards SET package_id = ? WHERE id = ?;");
            ps.setString(1, packages.getId());
            ps.setString(2, card.getId());

            ps.close();
            conn.close();;
            return this.findByCardId(card.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Card addCardToUser(Card card, User user){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = NULL, user_name = ? WHERE id = ?;");
            ps.setString(1, user.getUsername());
            ps.setString(2, card.getId());

            ps.close();
            conn.close();
            return this.findByCardId(card.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Card delete(Card card) {
        if (this.Cards.contains(card)){
            this.Cards.remove(card);
        }
        return null;
    }
}
