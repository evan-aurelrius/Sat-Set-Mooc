package com.satset.mooc.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_new_user")
public class DailyNewUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "new_instructor")
    private long new_instructor=0;
    @Column(name = "new_student")
    private long new_student=0;

    public DailyNewUser(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNew_instructor() {
        return new_instructor;
    }

    public void setNew_instructor(long new_instructor) {
        this.new_instructor = new_instructor;
    }

    public long getNew_student() {
        return new_student;
    }

    public void setNew_student(long new_student) {
        this.new_student = new_student;
    }
}
