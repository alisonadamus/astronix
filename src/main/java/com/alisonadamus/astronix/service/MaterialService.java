package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.Material;
import com.alisonadamus.astronix.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository repository;

    public List<Material> getFiltered(String name, Long locId, Long catId) {
        return repository.findByFilters(name, locId, catId);
    }

    public Material getById(Long id) {
        return repository.findById(id).orElse(new Material());
    }

    public void save(Material material) {
        repository.save(material);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}