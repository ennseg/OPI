package com.example.Beans;

import com.example.DataBase.DataBaseConnection;
import com.example.DataBase.DataBaseManager;
import com.example.DataBase.Point;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Named("tableBean")
@SessionScoped
public class TableBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<DotPoint> points = new ArrayList<>();

    @PostConstruct
    public void init() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        DataBaseManager dataBaseManager = new DataBaseManager(dataBaseConnection);

        try {
            for (Point point : dataBaseManager.getAllData()) {
                DotPoint dotPoint = new DotPoint(
                        point.getX(),
                        String.valueOf(point.getY()),
                        String.valueOf(point.getR()),
                        point.getSuccess()
                );
                points.add(dotPoint);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при загрузке точек из базы данных", e);
        }
    }

    public void addPoint(DotPoint point) {
        if (point != null) {
            points.add(point);
        }
    }

    public String getPointsJson() {
        return new Gson().toJson(points);
    }

    public void clear() throws SQLException {
        points.clear();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        DataBaseManager dataBaseManager = new DataBaseManager(dataBaseConnection);
        dataBaseManager.clearData();
    }
}
