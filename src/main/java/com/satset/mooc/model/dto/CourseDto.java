package com.satset.mooc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.model.Lecture;
import com.satset.mooc.model.Quiz;
import com.satset.mooc.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
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
}
