package auca.ac.rw.Online.quiz.management.controller;

import auca.ac.rw.Online.quiz.management.model.User;
import auca.ac.rw.Online.quiz.management.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final auca.ac.rw.Online.quiz.management.repository.LocationRepository locationRepository;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public UserController(UserRepository userRepository, 
                         auca.ac.rw.Online.quiz.management.repository.LocationRepository locationRepository,
                         com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Assessment Requirement: Implementation of Sorting and Pagination.
     * This method uses Spring Data JPA's Pageable and Sort interfaces.
     * Pageable: Handles page number and size.
     * Sort: Handles sorting by field and direction.
     * Using Pageable improves performance by retrieving only a subset of records (page) instead of the entire table.
     */
    @GetMapping
    public ResponseEntity<Page<User>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(userRepository.findAll(pageable));
    }

    /**
     * Assessment Requirement: Retrieve all users from a given province using province code OR province name.
     */
    @GetMapping("/by-province")
    public ResponseEntity<?> getUsersByProvince(
            @RequestParam(name = "provinceCode", required = false) Long provinceCode,
            @RequestParam(name = "provinceName", required = false) String provinceName) {
        System.out.println("DEBUG: getUsersByProvince called with code=" + provinceCode + ", name=" + provinceName);
        try {
            List<User> users;
            if (provinceCode != null) {
                System.out.println("DEBUG: Calling repository for code: " + provinceCode);
                users = locationRepository.findAllUsersInLocationHierarchy(provinceCode);
                System.out.println("DEBUG: Repository returned " + (users != null ? users.size() : "null") + " users");
            } else if (provinceName != null) {
                System.out.println("DEBUG: Finding province by name: " + provinceName);
                users = locationRepository.findByLocationType(auca.ac.rw.Online.quiz.management.model.LocationType.PROVINCE).stream()
                        .filter(l -> l.getName().equalsIgnoreCase(provinceName))
                        .findFirst()
                        .map(l -> {
                            System.out.println("DEBUG: Found province ID=" + l.getId() + ", calling hierarchy search");
                            return locationRepository.findAllUsersInLocationHierarchy(l.getId());
                        })
                        .orElse(List.of());
                System.out.println("DEBUG: Result list size: " + users.size());
            } else {
                users = List.of();
            }
            
            System.out.println("DEBUG: Starting serialization...");
            String json = objectMapper.writeValueAsString(users);
            System.out.println("DEBUG: Serialization successful. Returning response.");
            return ResponseEntity.ok(json);
        } catch (Throwable t) {
            System.err.println("CRITICAL ERROR in getUsersByProvince:");
            t.printStackTrace();
            java.io.StringWriter sw = new java.io.StringWriter();
            t.printStackTrace(new java.io.PrintWriter(sw));
            return ResponseEntity.status(500).body("Error retrieving users: " + sw.toString());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> create(@RequestBody User user) {
        if (user.getVillageId() != null) {
            locationRepository.findById(user.getVillageId())
                    .ifPresent(user::setLocation);
        }
        User saved = userRepository.save(user);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(user.getUsername());
                    existing.setPassword(user.getPassword());
                    existing.setEmail(user.getEmail());
                    existing.setRole(user.getRole());
                    
                    if (user.getVillageId() != null) {
                        locationRepository.findById(user.getVillageId())
                                .ifPresent(existing::setLocation);
                    } else if (user.getLocation() != null) {
                        existing.setLocation(user.getLocation());
                    }
                    
                    User updated = userRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


