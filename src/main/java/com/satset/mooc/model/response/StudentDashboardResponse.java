package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardResponse {

    private long enrolled_course;
    private long completed_course;
    private long completed_lecture;
    private long completed_quiz;
}
