package com.myproject.furnitureshop.enums;

import lombok.Getter;

@Getter
public enum CategoryLevel {
    ROOT(3),
    INTERMEDIATE(2),
    LEAF(1)
    ;

    CategoryLevel(int score) {
        this.score = score;
    }

    private final int score;
}
