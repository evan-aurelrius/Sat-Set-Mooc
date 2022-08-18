package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailLectureResponse {

    private long id;
    private String title;
    private String link;
    private Boolean is_completed = false;

}
