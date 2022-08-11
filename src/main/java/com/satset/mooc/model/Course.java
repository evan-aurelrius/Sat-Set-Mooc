package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "image")
    private String image;

    @Column(name = "timestamp")
    private Timestamp updated_at;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_enroll_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonIgnore
    private Set<Student> students = new LinkedHashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures = new LinkedList<>();

    @OneToMany(mappedBy = "course")
    private List<Quiz> quizzes = new LinkedList<>();

    public Course(String title, String description, String image, Timestamp updated_at, Instructor instructor, Set<Student> students, List<Lecture> lectures, List<Quiz> quizzes) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.updated_at = updated_at;
        this.instructor = instructor;
        this.students = students;
        this.lectures = lectures;
        this.quizzes = quizzes;
        this.status = "Pending";
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        updateTime();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        updateTime();
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        updateTime();
        this.image = image;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp updateTime() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
        return this.updated_at;
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

    public void setLectures(List<Lecture> lectures) {
        updateTime();
        this.lectures = lectures;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void addQuiz(Quiz quiz) {
        updateTime();
        this.quizzes.add(quiz);
    }

    public void setQuizzes(List<Quiz> quizzes) {
        updateTime();
        this.quizzes = quizzes;
    }
}
