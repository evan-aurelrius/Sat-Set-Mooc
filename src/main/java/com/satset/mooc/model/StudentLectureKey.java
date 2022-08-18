package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class StudentLectureKey implements Serializable {

    protected long student_id;
    protected long lecture_id;

    public StudentLectureKey(long student_id, long lecture_id) {
        this.student_id = student_id;
        this.lecture_id = lecture_id;
    }

}
