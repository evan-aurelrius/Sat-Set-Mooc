package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "opt_true")
    private String opt_true;

    @ElementCollection
    @CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name ="answer")
    private List<String> answers = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpt_true() {
        return opt_true;
    }

    public void setOpt_true(String opt_true) {
        this.opt_true = opt_true;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
