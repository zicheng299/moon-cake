package com.domain;

import lombok.Data;

@Data
public class Award {
    private String cake;
    private String name;
    private int count;


    public Award(String cake, String name, int count) {
        this.cake = cake;
        this.name = name;
        this.count = count;
    }
}
