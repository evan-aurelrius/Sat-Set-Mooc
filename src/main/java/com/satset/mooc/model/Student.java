package com.satset.mooc.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "image")
    private String image;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "timestamp")
    private Timestamp createdAt;

    @ManyToMany(mappedBy = "students")
    private List<Course> enrolledClass = new LinkedList<>();

    @ManyToMany(mappedBy = "students")
    private List<Lecture> lectures = new LinkedList<>();

    public Student(String name, String gender, String image, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.password = password;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.createdAt = createdAt;
    }

    public List<Course> getEnrolledClass() {
        return enrolledClass;
    }

    public void setEnrolledClass(List<Course> courseOwned) {
        this.enrolledClass = courseOwned;
    }

    public void addEnrolledClass(Course course) {
        this.enrolledClass.add(course);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
