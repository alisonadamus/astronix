package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.Material;
import com.alisonadamus.astronix.model.MaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByNameContainingIgnoreCase(String name);
    List<Material> findByCategories(Set<MaterialCategory> categories);
    @Query(""" 
            SELECT DISTINCT m FROM Material m LEFT JOIN m.categories c
            WHERE (:name IS NULL OR LOWER(m.name) LIKE %:name%)
            AND (:locId IS NULL OR m.location.id = :locId)
            AND (:catId IS NULL OR c.id = :catId)
            """)
    List<Material> findByFilters(@Param("name") String name,
                                 @Param("locId") Long locId,
                                 @Param("catId") Long catId);
}
