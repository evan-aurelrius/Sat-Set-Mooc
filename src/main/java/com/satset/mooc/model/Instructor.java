package com.satset.mooc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="instructor")
public class Instructor {
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

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "verified_status")
    private String verified_status = "Pending";

    @Column(name = "timestamp")
    private Timestamp created_at;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courseOwned = new LinkedHashSet<>();

    public Instructor(String name, String gender, String image, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.password = password;
        this.created_at = new Timestamp(System.currentTimeMillis());
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

    public Set<Course> getCourseOwned() {
        return courseOwned;
    }

    public void addCourseOwned(Course course) {
        this.courseOwned.add(course);
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

    public String getVerified_status() {
        return verified_status;
    }

    public void setVerified_status(String verified_status) {
        this.verified_status = verified_status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
}
