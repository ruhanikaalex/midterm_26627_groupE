package auca.ac.rw.Online.quiz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type; // e.g., DAILY/WEEKLY/MONTHLY or SCORE_SUMMARY

    private OffsetDateTime dateGenerated = OffsetDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "generated_by")
    @JsonIgnoreProperties("reports")
    private User generatedBy;

    // Optional links per ERD
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    /**
     * Assessment Requirement: Implementation of One-to-One relationship.
     * Each QuizAttempt can have exactly one Report generated for it.
     * This is mapped with unique=true in the JoinColumn to enforce the 1:1 constraint.
     */
    @OneToOne
    @JoinColumn(name = "attempt_id", unique = true)
    private QuizAttempt quizAttempt;

    private Double totalScore;

    @Column(length = 1000)
    private String note;

    public Report() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public OffsetDateTime getDateGenerated() { return dateGenerated; }
    public void setDateGenerated(OffsetDateTime dateGenerated) { this.dateGenerated = dateGenerated; }

    public User getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(User generatedBy) { this.generatedBy = generatedBy; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public QuizAttempt getQuizAttempt() { return quizAttempt; }
    public void setQuizAttempt(QuizAttempt quizAttempt) { this.quizAttempt = quizAttempt; }

    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}


