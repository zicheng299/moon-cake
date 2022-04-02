package com.domain;

import lombok.Data;

@Data
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
}
