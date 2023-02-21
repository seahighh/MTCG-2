package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.battle.Battle;
import org.example.application.game.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

class BattleMemoryRepositoryTest {
    static BattleMemoryRepository battleMemoryRepository;
    static UserMemoryRepository userMemoryRepository;
    @BeforeAll
    static void beforeAll() {
        battleMemoryRepository = battleMemoryRepository.getInstance();
        userMemoryRepository = userMemoryRepository.getInstance();
    }
    @BeforeEach
    void beforeEach() {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM battles WHERE id = -1");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBattle() {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO battles(id) VALUES(-1)");
            Battle battle = (Battle)battleMemoryRepository.getBattle(-1);
            sm.executeUpdate("DELETE FROM battles WHERE id = -1");
            sm.close();
            conn.close();
            Assertions.assertNotNull(battle);
            Assertions.assertEquals(-1, battle.getId());
        } catch (SQLException var4) {
            var4.printStackTrace();
        }
    }

    @Test
    void addBattle() throws SQLException {
        Battle battle = (Battle)battleMemoryRepository.addBattle();
        Assertions.assertNotNull(battle);
        Connection conn = Database.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT id FROM battles WHERE id = ?;");
        ps.setInt(1, battle.getId());
        ResultSet rs = ps.executeQuery();
        Assertions.assertTrue(rs.next());
        Assertions.assertEquals(battle.getId(), rs.getInt(1));
        Statement sm = conn.createStatement();
        sm.executeUpdate("DELETE FROM battles WHERE id = " + battle.getId() + ";");
        sm.close();
        rs.close();
        ps.close();
        conn.close();

    }

    @Test
    void addUserToBattle() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String usernameA = "test";
        String userpw = "testpassword";
        User player = new User();
        player.setUsername(usernameA);
        player.setPassword(userpw);
        player.setCoins(20);
        player.setToken("111");
        player.setStatus("1");
        userMemoryRepository.save(player);
        player = userMemoryRepository.findByUsername(usernameA);
        Battle battle = battleMemoryRepository.addBattle();
        battleMemoryRepository.addUserToBattle(player, battle);
        PreparedStatement ps = conn.prepareStatement("SELECT id, player_a, player_b FROM battles WHERE id=?;");
        ps.setInt(1, battle.getId());
        ResultSet rs = ps.executeQuery();
        Assertions.assertTrue(rs.next());
        Assertions.assertEquals(player.getId(), rs.getInt(2));
        Assertions.assertEquals(0, rs.getInt(3));
        Statement sm = conn.createStatement();
        sm.executeUpdate("DELETE FROM battles WHERE id = " + battle.getId() + ";");
        sm.executeUpdate("DELETE FROM users WHERE username = '"+"test"+"';");
        rs.close();
        ps.close();
        conn.close();
    }
}