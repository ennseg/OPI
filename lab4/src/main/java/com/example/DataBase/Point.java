package com.example.DataBase;

import lombok.Getter;

public class Point {
    public final int id;
    @Getter
    public final double x;
    @Getter
    public final double y;
    @Getter
    public final double r;
    public final boolean success;

    public Point(int id, double x, double y, double r, boolean success) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", success=" + success +
                '}';
    }
}