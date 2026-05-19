package com.alisonadamus.astronix.repository;

import com.alisonadamus.astronix.model.Result;
import com.alisonadamus.astronix.model.Task;
import com.alisonadamus.astronix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByUserAndTask(User user, Task task);

    List<Result> findByUserAndCompleted(User user, boolean completed);
}
