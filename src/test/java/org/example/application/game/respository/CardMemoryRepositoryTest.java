package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CardMemoryRepositoryTest {

    static CardMemoryRepository cardMemoryRepository;
    @BeforeAll
    static void beforeAll() {
        cardMemoryRepository = cardMemoryRepository.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        // Delete user with id -1 before every test case
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM cards WHERE id = '"+"id"+"';");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() throws SQLException{
        Card card = new Card();
        card.setId("id");
        card.setName("testSpell");
        card.setDamage(10);
        card = cardMemoryRepository.save(card);
        Connection conn = Database.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("select id,name,damage,element_type, card_type from cards WHERE id = ?;");
        ps.setString(1, card.getId());
        ResultSet rs = ps.executeQuery();
        Assertions.assertTrue(rs.next());
        Assertions.assertEquals("id", rs.getString(1));
        Assertions.assertEquals("testSpell", rs.getString(2));
        Assertions.assertEquals(10, rs.getInt(3));
        Assertions.assertEquals("Normal", rs.getString(4));
        Assertions.assertEquals("Spell", rs.getString(5));
        Statement sm = conn.createStatement();
        ps.close();
        sm.close();
        conn.close();


    }

    @Test
    void Delete() {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO cards(id, name, damage, element_type, card_type) VALUES('id', 'testname', 100, 'WATER', 'MONSTER');");
            boolean result = cardMemoryRepository.delete("id");
            ResultSet rs = sm.executeQuery("SELECT * FROM cards WHERE id = '"+"id"+"';");
            Assertions.assertTrue(result);
            Assertions.assertFalse(rs.next());
            sm.close();
            conn.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
    }
    @Test
    void findByCardId() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO cards(id, name, damage, element_type, card_type) VALUES('id', 'test', 100, 'WATER', 'MONSTER');");
        Card card;
        card = cardMemoryRepository.findByCardId("id");
        Assertions.assertEquals("test", card.getName());
        Assertions.assertEquals("id", card.getId());
        sm.close();
        conn.close();

    }

    @Test
    void findAll() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO cards(id, name, damage, element_type, card_type) VALUES('id', 'test', 100, 'WATER', 'MONSTER');");
        List<Card> cards = new ArrayList<>();
        cards = cardMemoryRepository.findAll();
        Assertions.assertTrue(cards.size()>0);
        sm.close();
        conn.close();


    }

}