package com.satset.mooc.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="student_lecture_status")
public class StudentLecture {

    @EmbeddedId
    StudentLectureKey studentLectureKey;

}
