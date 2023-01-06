package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.stats.Stats;
import org.example.application.game.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public boolean updateEloForPlayers(User playerA, User playerB, double pA, double pB) {
        int eloA = this.getStatsForUser(playerA).getElo();
        int eloB = this.getStatsForUser(playerB).getElo();

        double eA = 1 / (1 + Math.pow(10, (eloB - eloA) / 400.0));
        double eB = 1 - eA;

        int rEloA = (int) Math.round(eloA + 10 * (pA - eA));
        int rEloB = (int) Math.round(eloB + 10 * (pB - eB));

        try {
            Connection conn = Database.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("UPDATE users SET elo = ? WHERE id = ?;");
            ps.setInt(1, rEloA);
            ps.setInt(2, playerA.getId());
            if (ps.executeUpdate() <= 0) {
                return false;
            }

            ps = conn.prepareStatement("UPDATE users SET elo = ? WHERE id = ?;");
            ps.setInt(1, rEloB);
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

    public List<Map> getScoreboard() {
        try {
            Connection conn = Database.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT username, elo, total_battles, won_battles, lost_battles FROM users ORDER BY elo DESC;");
            ResultSet rs = ps.executeQuery();
            Map map = new HashMap();

            List<Map> list = new ArrayList<>();
            while (rs.next()) {
                String string = new String();
                map.put("username", rs.getString(1));
                map.put("elo", rs.getInt(2));
                map.put("total_battles", rs.getInt(3));
                map.put("won_battles", rs.getInt(4));
                map.put("lost_battles", rs.getInt(5));
                list.add(map);
            }

            ps.close();
            rs.close();
            conn.close();


            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
