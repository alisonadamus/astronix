package com.alisonadamus.astronix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String content; // Markdown контент

    private Integer estimatedTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    @JoinTable(name = "material_categories", joinColumns = @JoinColumn(name = "material_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<MaterialCategory> categories;
}