package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDto {

    private long user_id;
    private long id;

    private String title;

    private String description;

    private String status = "Pending";

    private String image;

    private Timestamp updated_at;

    private Instructor instructor;

    private Set<Student> students = new LinkedHashSet<>();

    @JsonProperty("lectures")
    private List<Lecture> lectures = new LinkedList<>();

    @JsonProperty("quizzes")
    private List<Quiz> quizzes = new LinkedList<>();

    public CourseDto(String title, String description, String image, Instructor instructor, Set<Student> students, List<Lecture> lectures, List<Quiz> quizzes) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.updated_at = new Timestamp(System.currentTimeMillis());
        this.instructor = instructor;
        this.students = students;
        this.lectures = lectures;
        this.quizzes = quizzes;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
