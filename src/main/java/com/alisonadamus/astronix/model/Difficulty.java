package com.alisonadamus.astronix.model;

import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("ЛЕГКИЙ РІВЕНЬ"),
    MEDIUM("СЕРЕДНІЙ РІВЕНЬ"),
    HARD("СКЛАДНИЙ РІВЕНЬ");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }
}
