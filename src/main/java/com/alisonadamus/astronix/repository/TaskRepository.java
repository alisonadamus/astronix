package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByLocationIdOrderByOrderIndexAsc(Long locationId);

    List<Task> findAllByOrderByOrderIndexAsc();

    @Query("SELECT t.orderIndex FROM Task t WHERE t.location.id = :locationId ORDER BY t.orderIndex")
    List<Integer> findOccupiedIndicesByLocationId(@Param("locationId") Long locationId);
}