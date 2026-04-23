package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long > {
    Optional<Location> findByName(String name);
    List<Location> findByNameContainingIgnoreCase(String name);
}
