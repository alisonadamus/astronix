package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.model.Result;
import com.alisonadamus.astronix.model.Task;
import com.alisonadamus.astronix.model.User;
import com.alisonadamus.astronix.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;


    public Optional<Result> getResult(User currentUser, Task task) {
        return resultRepository.findByUserAndTask(currentUser, task);
    }

    public Map<Long, Integer> calculateLocationsProgress(List<Location> locations, User user) {
        Map<Long, Integer> locationProgressMap = new HashMap<>();

        List<Result> userCompletedResults = resultRepository.findByUserAndCompleted(user, true);

        Set<Long> completedTaskIds = userCompletedResults.stream()
                .map(r -> r.getTask().getId())
                .collect(Collectors.toSet());

        for (Location loc : locations) {
            List<Task> totalTasks = loc.getTasks();

            if (totalTasks == null || totalTasks.isEmpty()) {
                locationProgressMap.put(loc.getId(), 0);
                continue;
            }

            long completedCount = totalTasks.stream()
                    .filter(task -> completedTaskIds.contains(task.getId()))
                    .count();

            int progressPercent = (int) ((completedCount * 100) / totalTasks.size());
            locationProgressMap.put(loc.getId(), progressPercent);
        }

        return locationProgressMap;
    }
}
