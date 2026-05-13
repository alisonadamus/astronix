package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.Result;
import com.alisonadamus.astronix.model.Task;
import com.alisonadamus.astronix.model.User;
import com.alisonadamus.astronix.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;


    public Optional<Result> getResult(User currentUser, Task task) {
        return resultRepository.findByUserAndTask(currentUser, task);
    }
}
