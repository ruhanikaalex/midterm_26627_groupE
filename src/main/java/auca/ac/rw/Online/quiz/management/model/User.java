package auca.ac.rw.Online.quiz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUserRole role = EUserRole.STUDENT;

    /**
     * Assessment Requirement: Implementation of One-to-Many relationship.
     * Many Users can belong to one Location. The foreign key location_id is stored in Users table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    // One user (teacher) can create many quizzes
    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Quiz> quizzesCreated = new ArrayList<>();

    // One user (student) can have many quiz attempts
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

    // One user can receive many notifications
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "generatedBy")
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    @Transient
    @JsonProperty("village_id")
    private Long villageId;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    @JsonIgnore
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    @JsonIgnore
    public List<Quiz> getQuizzesCreated() { return quizzesCreated; }
    public void setQuizzesCreated(List<Quiz> quizzesCreated) { this.quizzesCreated = quizzesCreated; }

    @JsonIgnore
    public List<QuizAttempt> getQuizAttempts() { return quizAttempts; }
    public void setQuizAttempts(List<QuizAttempt> quizAttempts) { this.quizAttempts = quizAttempts; }

    @JsonIgnore
    public List<Notification> getNotifications() { return notifications; }
    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }

    @JsonIgnore
    public List<Report> getReports() { return reports; }
    public void setReports(List<Report> reports) { this.reports = reports; }

    public Long getVillageId() { return villageId; }
    public void setVillageId(Long villageId) { this.villageId = villageId; }
}



