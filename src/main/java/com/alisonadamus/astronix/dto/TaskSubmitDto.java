package com.alisonadamus.astronix.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TaskSubmitDto {
    // Для SINGLE_CHOICE, MULTIPLE_CHOICE та ORDER
    private List<Long> answerIds;

    // Для TEXT
    private String textAnswer;

    // Для MATCHING
    private Map<Long, String> matchingAnswers;
}