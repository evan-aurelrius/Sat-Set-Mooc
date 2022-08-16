package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name="student_quiz")
public class StudentQuiz {

    @EmbeddedId
    private StudentQuizKey studentQuizKey;

    @Column(name = "")
    private int score;

    public StudentQuiz(StudentQuizKey studentQuizKey) {
        this.studentQuizKey = studentQuizKey;
        this.score = -1;
    }

    public StudentQuizKey getStudentQuizKey() {
        return studentQuizKey;
    }

    public void setStudentQuizKey(StudentQuizKey studentQuizKey) {
        this.studentQuizKey = studentQuizKey;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
