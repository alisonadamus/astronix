package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.model.*;
import com.alisonadamus.astronix.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final UserService userService;
    private final LocationService locationService;
    private final MaterialService materialService;
    private final MaterialCategoryService materialCategoryService;
    private final TaskService taskService;
    private final ResultService resultService;

    @GetMapping
    public String showLocationsPage(@RequestParam(value = "search", required = false)
                                        String search, Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName());
        List<Location> locations = locationService.searchLocations(search);

        Map<Long, Integer> locationProgressMap = resultService.calculateLocationsProgress(locations, user);

        model.addAttribute("locations", locations);
        model.addAttribute("locationProgressMap", locationProgressMap);
        model.addAttribute("searchQuery", search);
        model.addAttribute("showSearch", true);
        return "locations";
    }

    @GetMapping("/{id}")
    public String showLocationDetails(
            @PathVariable Long id, Principal principal,
            @RequestParam(required = false) String materialSearch,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) TaskType taskType,
            @RequestParam(required = false) Difficulty difficulty,
            Model model) {

        Location location = locationService.getById(id);
        User user = userService.findByEmail(principal.getName());
        List<Task> filteredTasks = taskService.search(id, taskType, difficulty);

        Map<Long, Boolean> tasksLockStatus = new HashMap<>();
        for (Task task : filteredTasks) {
            tasksLockStatus.put(task.getId(), taskService.isTaskLocked(task, user));
        }

        model.addAttribute("location", location);
        model.addAttribute("materials", materialService.search(id, materialSearch, categoryId));
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("tasksLockStatus", tasksLockStatus);

        model.addAttribute("allCategories", materialCategoryService.getAll(null));
        model.addAttribute("taskTypes", TaskType.values());
        model.addAttribute("difficulties", Difficulty.values());

        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedTaskType", taskType);
        model.addAttribute("selectedDifficulty", difficulty);
        model.addAttribute("materialSearch", materialSearch);

        return "location-details";
    }
}