package com.satset.mooc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.satset.mooc.model.Course;
import com.satset.mooc.model.Question;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class QuizDto {

    private long id;

    private String title;

    @JsonProperty("questions")
    private List<Question> questions = new LinkedList<>();

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
