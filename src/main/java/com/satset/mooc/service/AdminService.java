package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.response.AdminSummaryResponse;
import com.satset.mooc.model.response.DailyNewUserResponse;
import com.satset.mooc.model.response.TopCourseResponse;

import java.util.LinkedList;
import java.util.List;

public interface AdminService {

    Boolean authenticate(String email, String password);

    Admin getAdminByEmail(String email);

    void save(Admin admin);

    Admin getAdminById(long admin_id);

    List<AdminSummaryResponse> getSummary();

    List<TopCourseResponse> getTopCourse();

    LinkedList<DailyNewUserResponse> getDailyNewUser();
}
