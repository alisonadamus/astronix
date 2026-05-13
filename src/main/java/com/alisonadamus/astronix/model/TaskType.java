package com.alisonadamus.astronix.model;

import lombok.Getter;

@Getter
public enum TaskType {
    SINGLE_CHOICE("Одна відповідь"),
    MULTIPLE_CHOICE("Декілька відповідей"),
    MATCHING("Відповідності"),
    ORDER("Впорядкування"),
    TEXT("Письмова відповідь");

    private final String name;

    TaskType(String name) {
        this.name = name;
    }

}