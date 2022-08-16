package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private long id;
    private String title;
    private String image;
    private long enrolled_student;
    private String instructor_name;
}