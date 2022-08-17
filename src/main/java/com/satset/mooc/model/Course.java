package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    private String status = "Pending";

    @Column(name = "image")
    private String image;

    @Column(name = "timestamp")
    private Timestamp updated_at;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_enroll_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonIgnore
    private Set<Student> students = new LinkedHashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures = new LinkedList<>();

    @OneToMany(mappedBy = "course")
    private List<Quiz> quizzes = new LinkedList<>();

    @ElementCollection
    @CollectionTable(name = "course_order", joinColumns = @JoinColumn(name = "course_id"))
    private List<String> courseOrder = new LinkedList<>();

    public Course(String title, String description, String image, Instructor instructor, Set<Student> students, List<Lecture> lectures, List<Quiz> quizzes, List<String> courseOrder) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.updated_at = updateTime();
        this.instructor = instructor;
        this.students = students;
        this.lectures = lectures;
        this.quizzes = quizzes;
        this.courseOrder = courseOrder;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp updateTime() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
        return this.updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void addStudents(Student student) {
        updateTime();
        this.students.add(student);
    }

    public void setStudents(Set<Student> students) {
        updateTime();
        this.students = students;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLecture(Lecture lecture) {
        updateTime();
        this.lectures.add(lecture);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void addQuiz(Quiz quiz) {
        updateTime();
        this.quizzes.add(quiz);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<String> getCourseOrder() {
        return courseOrder;
    }

    public void addOrder(String newOrder) {
        this.courseOrder.add(newOrder);
    }

    public void setCourseOrder(List<String> courseOrder) {
        this.courseOrder = courseOrder;
    }
}
