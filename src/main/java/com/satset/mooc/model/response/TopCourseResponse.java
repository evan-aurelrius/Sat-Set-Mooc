package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCourseResponse {

    private String name;
    private long students;

}
