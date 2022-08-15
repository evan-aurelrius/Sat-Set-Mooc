package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class StudentQuizKey implements Serializable {

    private long student_id;
    private long quiz_id;

    public StudentQuizKey(long student_id, long quiz_id) {
        this.student_id = student_id;
        this.quiz_id = quiz_id;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(long quiz_id) {
        this.quiz_id = quiz_id;
    }
}
