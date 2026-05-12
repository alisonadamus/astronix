package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.service.LocationService;
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
    private final TaskService taskService;

    @GetMapping
    public String showLocationsPage(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("locations", locationService.searchLocations(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("showSearch", true);
        return "locations";
    }

    @GetMapping("/{id}")
    public String showLocationDetails(@PathVariable Long id, Model model) {
        Location location = locationService.getById(id);

        model.addAttribute("location", location);
        model.addAttribute("materials", materialService.getByLocation(id));
        model.addAttribute("tasks", taskService.getTasksByLocation(id));

        return "location-details";
    }
}