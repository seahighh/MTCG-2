package org.example.application.game.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements DatabaseInterface{

    private static Database instance;

    private static String DB_URL = "jdbc:postgresql://localhost:5432/swe1db";

    private static String DB_USER = "swe1user";

    private static String DB_PW = "swe1pw";

    public  static Database getInstance() {
        if (Database.instance == null) {
            Database.instance = new Database();
        }
        return Database.instance;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
