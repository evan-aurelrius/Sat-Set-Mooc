package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCourseResponse {

    private long id;
    private String title;
    private String image;
    private long enrolled_student;
    private String status;
    private Timestamp updated_at;

}
