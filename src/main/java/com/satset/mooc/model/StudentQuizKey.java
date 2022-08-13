package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class StudentQuizKey implements Serializable {

    private long student_id;
    private long course_id;

    public StudentQuizKey(long student_id, long course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }
}
