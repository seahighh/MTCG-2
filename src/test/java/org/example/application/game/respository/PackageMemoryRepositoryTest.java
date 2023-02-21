package org.example.application.game.respository;

import org.example.application.game.Database.Database;
import org.example.application.game.model.card.Package;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class PackageMemoryRepositoryTest {
    static PackageMemoryRepository packageMemoryRepository;

    @BeforeAll
    static void beforeAll() {
        packageMemoryRepository = packageMemoryRepository.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        // Delete user with id -1 before every test case
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM packages WHERE id = -1");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPackage() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO packages(id, price) VALUES(-1, 20)");
        Package newPackage;
        newPackage = packageMemoryRepository.getPackage(-1);
        Assertions.assertEquals(20, newPackage.getPrice());
        Assertions.assertEquals(-1, newPackage.getId());
        sm.close();
        conn.close();
    }

    @Test
    void addPackage(){

        Package cardPackage = packageMemoryRepository.addPackage();

        Assertions.assertEquals(5, cardPackage.getPrice());
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM packages WHERE id ='"+cardPackage.getId()+"';");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getPackages() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO packages(id, price) VALUES(-1, 20)");
        List<Package> packages = new ArrayList<>();
        packages = packageMemoryRepository.getPackages();
        Assertions.assertTrue(packages.size()>0);
        sm.close();
        conn.close();
    }

    @Test
    void getRandomPackage() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO packages(id, price) VALUES(-1, 20)");
        Package newPackage;
        newPackage = packageMemoryRepository.getRandomPackage();
        Assertions.assertNotNull(newPackage);

        sm.close();
        conn.close();
    }

    @Test
    void deletePackages() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        Statement sm = conn.createStatement();
        sm.executeUpdate("INSERT INTO packages(id, price) VALUES(-1, 20)");
        boolean result = packageMemoryRepository.deletePackages(-1);
        ResultSet rs = sm.executeQuery("select * from users where id = -1");
        Assertions.assertTrue(result);
        sm.close();
        conn.close();

    }
}