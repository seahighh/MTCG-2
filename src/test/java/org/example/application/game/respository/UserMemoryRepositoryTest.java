package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class UserMemoryRepositoryTest {

    static UserMemoryRepository userMemoryRepository;
    @BeforeAll
    static void beforeAll() {
        userMemoryRepository = userMemoryRepository.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        // Delete user with id -1 before every test case
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM users WHERE id = -1;");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findAll() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");
        List<User> users = userMemoryRepository.findAll();
        Assertions.assertTrue(users.size()>0);
    }

    @Test
    void getUserById() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");
        User user;
        user = userMemoryRepository.getUserById(-1);
        Assertions.assertEquals("user", user.getUsername());
        Assertions.assertEquals(-1, user.getId());
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void updateUser() throws SQLException{
        String username = "user";
        String password = "password";
        String Name = "name";
        String bio = "bio";
        String image = "image";
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, '"+ username+" ', '" + password + "')");
        User user = new User();
        user.setName(Name);
        user.setBio(bio);
        user.setImage(image);
        User user1;
        user1 = userMemoryRepository.updateUser(user);
        Assertions.assertEquals("name", user.getName());
        Assertions.assertEquals("bio", user.getBio());
        Assertions.assertEquals("image", user.getImage());
    }

    @Test
    void updateCoin() throws SQLException{
        int coins = 20;
        String username = "user";
        String password = "password";
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password, coins) VALUES(-1, '"+ username+" ', '" + password + "', '"+ coins+ "')");

        User user = new User();

        coins = coins - 5;
        user.setCoins(coins);
        User user1;
        user1 = userMemoryRepository.updateCoin(user);
        Assertions.assertEquals(15, user.getCoins());
    }

    @Test
    void findByUsername() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");
        User user;
        user = userMemoryRepository.findByUsername("user");
        Assertions.assertEquals("user", user.getUsername());
        Assertions.assertEquals(-1, user.getId());
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void delete() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'user', 'password')");
        User user = new User();
        boolean result = userMemoryRepository.delete("user");
        ResultSet rs = sm.executeQuery("select * from users where id = -1");
        Assertions.assertTrue(result);
        Assertions.assertFalse(rs.next());
    }
}