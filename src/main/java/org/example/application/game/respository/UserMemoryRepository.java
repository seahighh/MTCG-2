package org.example.application.game.respository;

import com.google.common.hash.Hashing;
import org.example.application.game.Database.Database;
import org.example.application.game.model.user.User;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {

    private final List<User> users;
    private static UserMemoryRepository instance;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    public static UserMemoryRepository getInstance() {
        if (UserMemoryRepository.instance == null) {
            UserMemoryRepository.instance = new UserMemoryRepository();
        }
        return UserMemoryRepository.instance;
    }

    @Override
    public List<User> findAll() {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users;");
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setCoins(rs.getInt("coins"));
                user.setStatus(rs.getString("status"));
                users.add(user);
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
    public User getUserById(int id) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setCoins(rs.getInt("coins"));
                user.setStatus(rs.getString("status"));
                user.setElo(rs.getInt("elo"));
                rs.close();
                ps.close();
                conn.close();

                return user;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps =conn.prepareStatement("SELECT * FROM users WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){

                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getString("status"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setCoins(rs.getInt("coins"));
                user.setName(rs.getString("call_name"));
                user.setBio(rs.getString("bio"));
                user.setImage(rs.getString("image"));
                rs.close();
                ps.close();
                conn.close();
                return user;
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
    public User updateCoin(User user) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users set coins = ? WHERE username = ?;");
            ps.setInt(1, user.getCoins());
            ps.setString(2, user.getUsername());

            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 0) {
                return null;
            }


//            return this.findByUsername(user.getUsername());
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(String username, String password) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT username, password FROM users WHERE username = ? AND password = ?;");
            String passwordHash = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                rs.close();
                ps.close();
                conn.close();
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


//    @Override
////    public User updateUser(String name, User user){
////        Connection conn = Database.getInstance().getConnection();
////        User preUser = this.findByUsername(name);
////        try {
////            PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ?, password = ?, token = ?,status = ?,coins = ? WHERE usernam = ?;");
////            ps.setString(1, user.getUsername() !=null ? user.getUsername() : preUser.getUsername());
////            ps.setString(2, user.getPassword() !=null ? user.getPassword() : preUser.getPassword());
////            ps.setString(3, user.getToken() !=null? user.getToken() : preUser.getToken());
////            ps.setString(4, user.getStatus() !=null ? user.getStatus() : preUser.getStatus());
////            ps.setInt(5, user.getCoins());
////            ps.setString(6, user.getUsername());
////
////            ps.close();
////            conn.close();
////            return this.findByUsername(name);
////
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////
////
////    }


    public User updateUser(User user) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users set call_name = ?, image = ?, bio = ? WHERE username = ?;");
            ps.setString(1, user.getName());
            ps.setString(2, user.getImage());
            ps.setString(3, user.getBio());
            ps.setString(4, user.getUsername());

            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 0) {
                return null;
            }

          //  return this.findByUsername(user.getUsername());
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String username) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            ps.setString(1, username);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return false;
            }

            ps.close();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
