package com.satset.mooc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
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

}
