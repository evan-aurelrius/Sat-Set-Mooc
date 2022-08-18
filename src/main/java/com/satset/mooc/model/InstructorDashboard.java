package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "instructor_dashboard")
public class InstructorDashboard {

    @Id
    @Column(name = "id", updatable = false)
    private long id;
    private int created_course = 0;
    private int verified_course = 0;
    private int pending_course = 0;
    private int rejected_course = 0;

    public InstructorDashboard(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCreated_course() {
        return created_course;
    }

    public void setCreated_course(int created_course) {
        this.created_course = created_course;
    }

    public int getVerified_course() {
        return verified_course;
    }

    public void setVerified_course(int verified_course) {
        this.verified_course = verified_course;
    }

    public int getPending_course() {
        return pending_course;
    }

    public void setPending_course(int pending_course) {
        this.pending_course = pending_course;
    }

    public int getRejected_course() {
        return rejected_course;
    }

    public void setRejected_course(int rejected_course) {
        this.rejected_course = rejected_course;
    }
}
