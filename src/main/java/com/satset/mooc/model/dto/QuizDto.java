package com.satset.mooc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.satset.mooc.model.Course;
import com.satset.mooc.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuizDto {

    private long id;

    private String title;

    @JsonProperty("questions")
    private List<Question> questions;

    private Course course;

    public QuizDto(String title, List<Question> questions, Course course) {
        this.title = title;
        this.questions = questions;
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
