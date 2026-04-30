package com.alisonadamus.astronix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matching_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingAnswer extends Answer {

    @Column(nullable = false)
    private String leftText;

    @Column(nullable = false)
    private String rightText;
}