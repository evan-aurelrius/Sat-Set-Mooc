package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseResponse {

    private long id;
    private String title;
    private String image;
    private String instructor_name;
    private long total_content;
    private long completed_content;
}
