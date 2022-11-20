package org.example.application.game.respository;

import org.example.Database.Database;
import org.example.application.game.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {

    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public User findByUsername(String username) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps =conn.prepareStatement("SELECT username, password FROM users WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User user = new User();
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));

                rs.close();
                ps.close();
                conn.close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User save(User user) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username, password) VALUES(?. ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
//            ps.setString(3, user.getToken());
//            ps.setString(4, user.getStatus());
            ps.close();;
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User delete(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }

        return user;
    }
}
