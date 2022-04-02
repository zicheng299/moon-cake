package com.domain;

public class Player {
    private String name;
    private String prize;
    private int index;

    public Player() {
    }

    public Player(String name, String prize, int index) {
        this.name = name;
        this.prize = prize;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
