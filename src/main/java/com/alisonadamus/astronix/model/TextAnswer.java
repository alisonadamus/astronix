package com.alisonadamus.astronix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "text_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextAnswer extends Answer {

    @Column(nullable = false)
    private String correctAnswer;
}