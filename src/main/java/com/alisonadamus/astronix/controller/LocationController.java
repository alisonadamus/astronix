package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public String showLocationsPage(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("locations", locationService.searchLocations(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("showSearch", true);
        return "locations";
    }
}