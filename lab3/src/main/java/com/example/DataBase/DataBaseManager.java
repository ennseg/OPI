package com.example.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private final DataBaseConnection dbconnection;

    public DataBaseManager(DataBaseConnection dbconnection) {
        this.dbconnection = dbconnection;
    }

    public void addData(Double x, Double y, Double r, Boolean success) throws SQLException {
        String sql = "INSERT INTO points (x, y, r, success) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, x);
            statement.setDouble(2, y);
            statement.setDouble(3, r);
            statement.setBoolean(4, success);
            statement.executeUpdate();
        }
    }

    public List<Point> getAllData() throws SQLException {
        String sql = "SELECT id, x, y, r, success FROM points";
        List<Point> points = new ArrayList<>();

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double r = rs.getDouble("r");
                boolean success = rs.getBoolean("success");

                points.add(new Point(id, x, y, r, success));
            }
        }

        return points;
    }

    public void clearData() throws SQLException {
        String sql = "DELETE FROM points";

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }



}
