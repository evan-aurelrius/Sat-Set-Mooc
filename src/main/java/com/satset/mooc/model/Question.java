package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "opt_true")
    private String opt_true;

    @ElementCollection
    @JsonProperty("opt")
    @CollectionTable(name = "question_answer", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> opt = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    public Question(String question, String opt_true, List<String> opt) {
        this.question = question;
        this.opt_true = opt_true;
        this.opt = opt;
    }

    public long getId() {
        return id;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public List<String> getOpt() {
        return opt;
    }

    public void setOpt(List<String> opt) {
        this.opt = opt;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
