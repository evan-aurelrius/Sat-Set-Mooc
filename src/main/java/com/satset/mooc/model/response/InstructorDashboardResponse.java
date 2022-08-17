package com.satset.mooc.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDashboardResponse {

    private int created_course;
    private int verified_course;
    private int pending_course ;
    private int rejected_course;

}
