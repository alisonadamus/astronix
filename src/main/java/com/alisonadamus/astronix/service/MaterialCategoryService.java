package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.MaterialCategory;
import com.alisonadamus.astronix.repository.MaterialCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialCategoryService {
    private final MaterialCategoryRepository repository;

    public List<MaterialCategory> getAll(String search) {
        if (search != null && !search.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(search);
        }
        return repository.findAll();
    }

    public MaterialCategory getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Категорія не знайдена"));
    }

    public void save(MaterialCategory category) {
        repository.save(category);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}