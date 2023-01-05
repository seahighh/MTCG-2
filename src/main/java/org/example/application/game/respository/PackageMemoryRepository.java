package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PackageMemoryRepository implements PackageRepository{
    private static PackageMemoryRepository instance;

    private UserMemoryRepository userMemoryRepository;
    private CardMemoryRepository cardMemoryRepository;
    public PackageMemoryRepository() {
        userMemoryRepository = UserMemoryRepository.getInstance();
        cardMemoryRepository = CardMemoryRepository.getInstance();
    }
    public static PackageMemoryRepository getInstance() {
        if (PackageMemoryRepository.instance == null) {
            PackageMemoryRepository.instance = new PackageMemoryRepository();
        }
        return PackageMemoryRepository.instance;
    }
    public Package getPackage(int id){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, price FROM packages WHERE id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Package packages = Package.builder()
                        .id(rs.getInt(1))
                        .price(rs.getInt(2))
                        .build();
                rs.close();
                ps.close();
                conn.close();

                return packages;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Package getRandomPackage() {
        Connection conn = Database.getInstance().getConnection();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, price FROM packages ORDER BY id;"); //one data only

            if (rs.next()){
                Package packages = Package.builder()
                        .id(rs.getInt(1))
                        .price(rs.getInt(2))
                        .build();
                rs.close();
                sm.close();
                conn.close();

                return packages;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public List<Package> getPackages() {
        Connection conn = Database.getInstance().getConnection();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, price FROM packages");

            List<Package> packageList = new ArrayList<>();
            while (rs.next()){
                packageList.add(Package.builder()
                        .id(rs.getInt(1))
                        .price(rs.getInt(2))
                        .build());
            }

            rs.close();
            sm.close();
            conn.close();

            return packageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Package addPackage() {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(price, legal) VALUES(?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 5);
            ps.setInt(2, 1);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getPackage(generatedKeys.getInt(1));
                }
            }

            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean addPackageToUser(Package packages, User user) {
        user = userMemoryRepository.findByUsername(user.getUsername());
        // Not enough coins
        if (user.getCoins() < 5){
            return false;
        }

        // Update coin balance

        user.setCoins(user.getCoins() - packages.getPrice());

        // Save user

        userMemoryRepository.updateCoin(user);

        for (Card card : cardMemoryRepository.getCardsForPackage(packages)){
            cardMemoryRepository.addCardToUser(card, user);
        }
        this.deletePackages(packages.getId());
        return true;
    }

    @Override
    public boolean deletePackages(int id){
        Connection conn = Database.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM packages WHERE id = ?");
            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
