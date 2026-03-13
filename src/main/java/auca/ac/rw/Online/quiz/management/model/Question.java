package auca.ac.rw.Online.quiz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EQuestionType type = EQuestionType.SINGLE_CHOICE;

    // Assessment Requirement: Implementation of Many-to-Many relationship
    @ManyToMany(mappedBy = "questions")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Quiz> quizzes = new ArrayList<>();

    @Column(length = 255)
    private String category; // for reusable question bank categorization

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("question")
    private List<Option> options = new ArrayList<>();

    public Question() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public EQuestionType getType() { return type; }
    public void setType(EQuestionType type) { this.type = type; }

    public List<Quiz> getQuizzes() { return quizzes; }
    public void setQuizzes(List<Quiz> quizzes) { this.quizzes = quizzes; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { 
        this.options = options;
        if (options != null) {
            for (Option o : options) {
                o.setQuestion(this);
            }
        }
    }
}


