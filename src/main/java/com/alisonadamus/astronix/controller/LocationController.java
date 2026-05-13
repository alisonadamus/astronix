package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.model.Difficulty;
import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.model.TaskType;
import com.alisonadamus.astronix.service.LocationService;
import com.alisonadamus.astronix.service.MaterialCategoryService;
import com.alisonadamus.astronix.service.MaterialService;
import com.alisonadamus.astronix.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final MaterialService materialService;
    private final MaterialCategoryService materialCategoryService;
    private final TaskService taskService;

    @GetMapping
    public String showLocationsPage(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("locations", locationService.searchLocations(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("showSearch", true);
        return "locations";
    }

    @GetMapping("/{id}")
    public String showLocationDetails(
            @PathVariable Long id,
            @RequestParam(required = false) String materialSearch,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) TaskType taskType,
            @RequestParam(required = false) Difficulty difficulty,
            Model model) {

        Location location = locationService.getById(id);

        model.addAttribute("location", location);
        model.addAttribute("materials", materialService.search(id, materialSearch, categoryId));
        model.addAttribute("tasks", taskService.search(id, taskType, difficulty));

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