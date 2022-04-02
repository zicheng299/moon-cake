package com.domain;

import lombok.Data;

@Data
public class Cake {
    private int point;
    private int count;


    public Cake(int point, int count) {
        this.point = point;
        this.count = count;
    }
}
