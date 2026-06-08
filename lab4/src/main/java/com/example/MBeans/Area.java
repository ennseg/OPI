package com.example.MBeans;

public class Area implements AreaMBean {
    private double r = 1.0;

    @Override
    public double getR() { return r; }

    @Override
    public double getArea() {
        double triangle  = 0.5 * r * r;
        double rectangle = (r / 2.0) * r;
        double semicircle = Math.PI * r * r / 2.0;
        return triangle + rectangle + semicircle;
    }

    @Override
    public void setR(double r) {
        this.r = r;
    }
}
