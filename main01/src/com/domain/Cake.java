package com.domain;

public class Cake {
    private int point;
    private int count;

    public Cake() {
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Cake(int point, int count) {
        this.point = point;
        this.count = count;
    }
}
