package com.alisonadamus.astronix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResultDto {
    private boolean correct;
    private String message;
    private String explanation;
    private int attempts;
}