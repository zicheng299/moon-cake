package com.domain;

public class Award {
    private String cake;
    private String name;
    private int count;

    public Award() {
    }

    public Award(String cake, String name, int count) {
        this.cake = cake;
        this.name = name;
        this.count = count;
    }

    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
