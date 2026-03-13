package auca.ac.rw.Online.quiz.management.controller;

import auca.ac.rw.Online.quiz.management.model.Location;
import auca.ac.rw.Online.quiz.management.model.User;
import auca.ac.rw.Online.quiz.management.model.LocationType;
import auca.ac.rw.Online.quiz.management.service.LocationService;
import auca.ac.rw.Online.quiz.management.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Location> getAll() { return locationService.getAllLocations(); }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return locationService.getLocationById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Assessment Requirement: Implementation of saving Location.
     * This method receives a Location object in the request body and persists it to the database.
     * The relationship is handled by assigning users to these locations.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Location location) {
        try {
            if (location.getParent() != null && location.getParent().getId() != null) {
                locationRepository.findById(location.getParent().getId())
                        .ifPresent(location::setParent);
            }
            Location saved = locationRepository.save(location);
            return ResponseEntity.created(URI.create("/api/locations/" + saved.getId())).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating location: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Location> update(@PathVariable Long id, @RequestBody Location location) {
        return locationRepository.findById(id)
                .map(existing -> {
                    existing.setName(location.getName());
                    existing.setLocationType(location.getLocationType());
                    if (location.getParent() != null && location.getParent().getId() != null) {
                        locationRepository.findById(location.getParent().getId())
                                .ifPresent(existing::setParent);
                    }
                    Location saved = locationRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{type}")
    public List<Location> getByType(@PathVariable LocationType type) {
        return locationRepository.findByLocationType(type);
    }

    @GetMapping("/children/{parentId}")
    public List<Location> getChildren(@PathVariable Long parentId) {
        return locationRepository.findByParentId(parentId);
    }

    @GetMapping("/users/{locationId}")
    public List<User> getUsersInLocation(@PathVariable Long locationId) {
        return locationRepository.findAllUsersInLocationHierarchy(locationId);
    }
}
