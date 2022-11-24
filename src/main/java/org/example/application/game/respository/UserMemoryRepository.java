package org.example.application.game.respository;

import org.example.Database.Database;
import org.example.application.game.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {

    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users;");
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while(rs.next()){
                users.add(User.builder()
                        .username(rs.getString(1))
                        .password(rs.getString(2))
                        .token(rs.getString(3))
                        .status(rs.getString(4))
                        .coins(rs.getInt(5))
                        .build());

            }
            rs.close();
            ps.close();
            conn.close();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username, password, token, status, coins) VALUES(?, ?, ?, ?, ?);");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getToken());
            ps.setString(4, user.getStatus());
            ps.setInt(5, user.getCoins());
            ps.execute();
            ps.close();;
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User updateUser(String name, User user){
        Connection conn = Database.getInstance().getConnection();
        User preUser = this.findByUsername(name);
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ?, password = ?, token = ?,status = ?,coins = ? WHERE usernam = ?;");
            ps.setString(1, user.getUsername() !=null ? user.getUsername() : preUser.getUsername());
            ps.setString(2, user.getPassword() !=null ? user.getPassword() : preUser.getPassword());
            ps.setString(3, user.getToken() !=null? user.getToken() : preUser.getToken());
            ps.setString(4, user.getStatus() !=null ? user.getStatus() : preUser.getStatus());
            ps.setInt(5, user.getCoins());
            ps.setString(6, user.getUsername());

            ps.close();
            conn.close();
            return this.findByUsername(name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public User delete(User user) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            ps.setString(1, user.getUsername());

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return user;
    }
}
