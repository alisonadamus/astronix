package com.alisonadamus.astronix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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