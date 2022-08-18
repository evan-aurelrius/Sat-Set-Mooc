package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailResponse {

    private long id;
    private String title;
    private String description;
    private String image;
    private Timestamp updated_at;
    private CourseDetailInstructorResponse instructor;
    private List<CourseDetailLectureResponse> lectures = new LinkedList<>();
    private List<CourseDetailQuizResponse> quizzes = new LinkedList<>();

}
