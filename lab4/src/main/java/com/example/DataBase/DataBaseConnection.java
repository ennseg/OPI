package com.example.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private final String URL = "jdbc:postgresql://" + System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT") + "/" + System.getenv("DB_NAME");
    private final String login = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASS");

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, login, password);
        } catch (SQLException e) {
            throw e;
        }
    }
}
