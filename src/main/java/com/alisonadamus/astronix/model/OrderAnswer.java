package com.alisonadamus.astronix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnswer extends Answer {

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer correctOrder;
}