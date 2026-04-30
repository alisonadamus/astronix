package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.dto.MaterialDTO;
import com.alisonadamus.astronix.model.*;
import com.alisonadamus.astronix.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LocationService locationService;
    private final MaterialCategoryService categoryService;
    private final MaterialService materialService;
    private final UserService userService;
    private final TaskService taskService;

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

    @GetMapping("/categories")
    public String listCategories(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("categories", categoryService.getAll(search));
        model.addAttribute("searchQuery", search);
        model.addAttribute("activeTab", "categories");
        return "admin/categories";
    }

    @GetMapping("/categories/add")
    public String showCategoryAddForm(Model model) {
        model.addAttribute("category", new MaterialCategory());
        model.addAttribute("activeTab", "categories");
        return "admin/category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute MaterialCategory category, RedirectAttributes ra) {
        categoryService.save(category);
        ra.addFlashAttribute("success", "Реєстр категорій оновлено.");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String showCategoryEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("activeTab", "categories");
        return "admin/category-form";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        categoryService.delete(id);
        ra.addFlashAttribute("success", "Категорію видалено.");
        return "redirect:/admin/categories";
    }

    @GetMapping("/materials")
    public String listMaterials(@RequestParam(required = false) String search,
                                @RequestParam(required = false) Long locationId,
                                @RequestParam(required = false) Long categoryId,
                                Model model) {
        model.addAttribute("materials", materialService.getFiltered(search, locationId, categoryId));
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("categories", categoryService.getAll(null));
        model.addAttribute("activeTab", "materials");
        return "admin/materials";
    }

    @GetMapping("/materials/add")
    public String showMaterialForm(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("categories", categoryService.getAll(null));
        model.addAttribute("activeTab", "materials");
        return "admin/material-form";
    }

    @PostMapping("/materials/save")
    public String saveMaterial(@ModelAttribute Material material) {
        materialService.save(material);
        return "redirect:/admin/materials";
    }

    @GetMapping("/materials/edit/{id}")
    public String showEditMaterialForm(@PathVariable Long id, Model model) {
        Material material = materialService.getById(id);
        model.addAttribute("material", material);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("categories", categoryService.getAll(null));
        model.addAttribute("activeTab", "materials");
        return "admin/material-form";
    }

    @GetMapping("/materials/delete/{id}")
    public String deleteMaterial(@PathVariable Long id, RedirectAttributes ra) {
        materialService.delete(id);
        ra.addFlashAttribute("success", "Матеріал видалено.");
        return "redirect:/admin/materials";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("activeTab", "tasks");
        return "admin/tasks";
    }

    @GetMapping("/tasks/add")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("types", TaskType.values());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("materials", Collections.emptyList());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("occupiedIndices", Collections.emptyList());
        model.addAttribute("activeTab", "tasks");
        return "admin/task-form";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getById(id);
        model.addAttribute("task", task);
        model.addAttribute("types", TaskType.values());
        model.addAttribute("difficulties", Difficulty.values());

        model.addAttribute("locations", locationService.getAllLocations());
        if (task.getLocation() != null) {
            model.addAttribute("materials", materialService.getByLocation(task.getLocation().getId()));
            model.addAttribute("occupiedIndices", taskService.getOccupiedIndices(task.getLocation().getId()));
        } else {
            model.addAttribute("occupiedIndices", Collections.emptyList());
            model.addAttribute("materials", Collections.emptyList());
        }
        model.addAttribute("activeTab", "tasks");
        return "admin/task-form";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task, HttpServletRequest request) {
        taskService.saveWithAnswers(task, request);
        return "redirect:/admin/tasks";
    }

    @GetMapping("/tasks/occupied-indices")
    @ResponseBody
    public List<Integer> getIndices(@RequestParam Long locationId) {
        return taskService.getOccupiedIndices(locationId);
    }

    @GetMapping("/tasks/materials-by-location")
    @ResponseBody
    public List<MaterialDTO> getMaterialsByLocation(@RequestParam Long locationId) {
        return materialService.getByLocation(locationId).stream()
                .map(m -> new MaterialDTO(m.getId(), m.getName()))
                .toList();
    }
}