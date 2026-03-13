package auca.ac.rw.Online.quiz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false)
    private LocationType locationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({"children", "users", "hibernateLazyInitializer", "handler"})
    private Location parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Location> children = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    /**
     * Assessment Requirement: Implementation of One-to-Many relationship.
     * One Location can be associated with Many Users.
     * This is mapped using mappedBy="location" in the User entity.
     */
    public Location() {}

    public Location(Long id, String name, LocationType locationType, Location parent) {
        this.id = id;
        this.name = name;
        this.locationType = locationType;
        this.parent = parent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }

    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }

    @JsonIgnore
    public List<Location> getChildren() { return children; }
    public void setChildren(List<Location> children) { this.children = children; }

    @JsonIgnore
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}


