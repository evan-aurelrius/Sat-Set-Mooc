package com.satset.mooc.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name="student_quiz")
public class StudentQuiz {

    @EmbeddedId
    private StudentQuizKey studentQuizKey;

    @Column()
    private int score;

    @Column()
    private String answer_feedback;

    public StudentQuiz(StudentQuizKey studentQuizKey) {
        this.studentQuizKey = studentQuizKey;
        this.score = -1;
        this.answer_feedback = "";
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

    public String getAnswer_feedback() {
        return answer_feedback;
    }

    public void setAnswer_feedback(String answer_feedback) {
        this.answer_feedback = answer_feedback;
    }
}
