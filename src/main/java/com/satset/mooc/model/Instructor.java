package com.satset.mooc.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name="instructor")
public class Instructor implements User{
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

    @Column(name = "role")
    private final String role = "instructor";

    @Column(name = "timestamp")
    private Timestamp created_at;

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courseOwned = new LinkedHashSet<>();

    public Instructor(String name, String gender, String image, String email, String password, String token) {
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.email = email;
        this.password = password;
        this.token = token;
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

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Course> getCourseOwned() {
        return courseOwned;
    }

    public void setCourseOwned(Set<Course> courseOwned) {
        this.courseOwned = courseOwned;
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

    public String printAllCourseName() {
        String s = "";
        for(Course c:courseOwned) {
            s += c.getTitle() + " ";
        }
        return s;
    }

}
