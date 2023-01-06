package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.stats.Stats;
import org.example.application.game.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatsMemoryRepository {

    private static StatsMemoryRepository instance;
    private final UserRepository userRepository;
    public StatsMemoryRepository(){
        userRepository = UserMemoryRepository.getInstance();

    }

    public static StatsMemoryRepository getInstance(){
        if (StatsMemoryRepository.instance == null){
            StatsMemoryRepository.instance = new StatsMemoryRepository();
        }
        return StatsMemoryRepository.instance;
    }

    public Stats getStatsForUser(User user){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT total_battles, won_battles, lost_battles, elo FROM  users WHERE id = ?;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Stats stats = Stats.builder()
                        .battles(rs.getInt(1))
                        .numbersOfWin(rs.getInt(2))
                        .numbersOfLost(rs.getInt(3))
                        .elo(rs.getInt(4))
                        .build();
                rs.close();
                ps.close();
                conn.close();

                return stats;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public Stats addStatForUser(User user, int stat) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps;

            if (stat > 0) {
                // Win
                ps = conn.prepareStatement("UPDATE users SET won_battles = won_battles+1, total_battles = total_battles+1 WHERE id = ?;");
            } else if (stat < 0) {
                // Loss
                ps = conn.prepareStatement("UPDATE users SET lost_battles = lost_battles+1, total_battles = total_battles+1 WHERE id = ?;");
            } else {
                // Tie
                ps = conn.prepareStatement("UPDATE users SET total_battles = total_battles+1 WHERE id = ?;");
            }

            ps.setInt(1, user.getId());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows > 0) {
                return this.getStatsForUser(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateEloForPlayers(User playerA, User playerB, int pA, int pB) {
        int eloA = this.getStatsForUser(playerA).getElo();
        int eloB = this.getStatsForUser(playerB).getElo();

        eloA += pA;
        eloB += pB;


        try {
            Connection conn = Database.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("UPDATE users SET elo = ? WHERE id = ?;");
            ps.setInt(1, eloA);
            ps.setInt(2, playerA.getId());
            if (ps.executeUpdate() <= 0) {
                return false;
            }

            ps = conn.prepareStatement("UPDATE users SET elo = ? WHERE id = ?;");
            ps.setInt(1, eloB);
            ps.setInt(2, playerB.getId());
            if (ps.executeUpdate() <= 0) {
                return false;
            }

            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Map getScoreboard(String username) {
        try {
            Connection conn = Database.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT username, elo, total_battles, won_battles, lost_battles, rank() OVER(ORDER BY elo)AS rank From users WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Map map = new LinkedHashMap();

            if (rs.next()) {
                map.put("username", rs.getString(1));
                map.put("elo", rs.getInt(2));
                map.put("total_battles", rs.getInt(3));
                map.put("won_battles", rs.getInt(4));
                map.put("lost_battles", rs.getInt(5));
                map.put("rank", rs.getInt(6));

            }

            ps.close();
            rs.close();
            conn.close();


            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
