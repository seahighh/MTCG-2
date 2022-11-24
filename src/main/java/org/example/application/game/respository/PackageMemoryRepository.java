package org.example.application.game.respository;

import org.example.Database.Database;
import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PackageMemoryRepository implements PackageRepository{
    public Package getPackage(String id){
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM packages WHERE id = ?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Package packages = Package.builder()
                        .id(rs.getString(1))
                        .name(rs.getString(2))
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
            ResultSet rs = sm.executeQuery("SELECT id, name FROM packages ORDER BY random() LIMIT 1;"); //one data only

            if (rs.next()){
                Package packages = Package.builder()
                        .id(rs.getString(1))
                        .name(rs.getString(2))
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
            ResultSet rs = sm.executeQuery("SELECT id, name FROM packages");

            List<Package> packageList = new ArrayList<>();
            while (rs.next()){
                packageList.add(Package.builder()
                        .id(rs.getString(1))
                        .name(rs.getString(2))
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
    public Package addPackage(Package packages) {
        Connection conn = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(name) VALUES (?);");
            ps.setString(1, packages.getName());

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public boolean addPackageToUser(Package packages, User user) {
        return false;
    }

    @Override
    public Package deletePackages(Package packages){
        Connection conn = Database.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM packages WHERE id = ?");
            ps.setString(1, packages.getId());

            ps.close();
            conn.close();
            return packages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
