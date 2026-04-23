package com.alisonadamus.astronix.service;

import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<Location> searchLocations(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllLocations();
        }
        return locationRepository.findByNameContainingIgnoreCase(query);
    }

    public void save(Location location) {
        locationRepository.save(location);
    }

    public Location getById(Long locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }

    public Location getByName(String locationName) {
        return locationRepository.findByName(locationName).orElse(null);
    }

    public void delete(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}