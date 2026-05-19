package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.Difficulty;
import com.alisonadamus.astronix.model.Task;
import com.alisonadamus.astronix.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByLocationIdOrderByOrderIndexAsc(Long locationId);

    List<Task> findAllByOrderByOrderIndexAsc();

    @Query("SELECT t.orderIndex FROM Task t WHERE t.location.id = :locationId ORDER BY t.orderIndex")
    List<Integer> findOccupiedIndicesByLocationId(@Param("locationId") Long locationId);
    @Query("SELECT t FROM Task t " +
            "WHERE t.location.id = :locId " +
            "AND (:tType IS NULL OR t.type = :tType) " +
            "AND (:diff IS NULL OR t.difficulty = :diff) " +
            "ORDER BY t.orderIndex ASC")
    List<Task> findFiltered(@Param("locId") Long locId, @Param("tType") TaskType tType, @Param("diff") Difficulty diff);

    Optional<Task> findFirstByLocationIdAndOrderIndexLessThanOrderByOrderIndexDesc(Long locationId, Integer currentOrderIndex);
}