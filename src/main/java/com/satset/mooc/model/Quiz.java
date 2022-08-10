package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name="quiz")
public class Quiz {
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
    private Set<String> answers = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
}
