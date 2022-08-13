package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name="lecture")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;


    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Column(name = "link", columnDefinition = "TEXT")
    @JsonProperty("link")
    private String link;

    public Lecture(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
}
