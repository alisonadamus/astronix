package com.alisonadamus.astronix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "choice_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceAnswer extends Answer {

    @Column(nullable = false)
    private String text;

    private boolean correct;
}