package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.MaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialCategoryRepository extends JpaRepository<MaterialCategory, Long> {
    List<MaterialCategory> findByNameContainingIgnoreCase(String name);
}
