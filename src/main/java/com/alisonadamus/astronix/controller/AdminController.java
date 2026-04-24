package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.model.Material;
import com.alisonadamus.astronix.model.MaterialCategory;
import com.alisonadamus.astronix.model.Role;
import com.alisonadamus.astronix.service.LocationService;
import com.alisonadamus.astronix.service.MaterialCategoryService;
import com.alisonadamus.astronix.service.MaterialService;
import com.alisonadamus.astronix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LocationService locationService;
    private final UserService userService;

    @GetMapping
    public String adminIndex() {
        return "redirect:/admin/locations";
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("users", userService.getAllUsers(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("roles", Role.values());
        model.addAttribute("activeTab", "users");
        return "admin/users";
    }

    @PostMapping("/users/update-role")
    public String updateRole(@RequestParam Long userId, @RequestParam Role role, RedirectAttributes ra) {
        userService.updateRole(userId, role);
        ra.addFlashAttribute("success", "Рівень доступу користувача змінено.");
        return "redirect:/admin/users";
    }

    @GetMapping("/locations")
    public String listLocations(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("locations", locationService.searchLocations(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("activeTab", "locations");
        return "admin/locations";
    }

    @GetMapping("/locations/add")
    public String showAddForm(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("activeTab", "locations");
        return "admin/location-form";
    }

    @PostMapping("/locations/save")
    public String saveLocation(@ModelAttribute Location location, RedirectAttributes ra) {
        locationService.save(location);
        ra.addFlashAttribute("success", "Локацію оновлено успішно!");
        return "redirect:/admin/locations";
    }

    @GetMapping("/locations/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationService.getById(id));
        model.addAttribute("activeTab", "locations");
        return "admin/location-form";
    }

    @GetMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable Long id, RedirectAttributes ra) {
        locationService.delete(id);
        ra.addFlashAttribute("success", "Локацію видалено.");
        return "redirect:/admin/locations";
    }

}