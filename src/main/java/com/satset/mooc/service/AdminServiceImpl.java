package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.response.AdminSummaryResponse;
import com.satset.mooc.model.response.DailyNewUserResponse;
import com.satset.mooc.model.response.TopCourseResponse;
import com.satset.mooc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseService courseService;
    @Autowired
    DailyNewUserService dailyNewUserService;

    @Override
    public Boolean authenticate(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).isPresent();
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(long admin_id) {
        return adminRepository.findById(admin_id).orElse(null);
    }

    @Override
    public List<AdminSummaryResponse> getSummary() {
        List<AdminSummaryResponse> lst = new ArrayList<>();
        lst.add(new AdminSummaryResponse("students", studentService.countAll()));
        lst.add(new AdminSummaryResponse("instructors", instructorService.countAll()));
        lst.add(new AdminSummaryResponse("courses", courseService.countAll()));
        return lst;
    }

    @Override
    public List<TopCourseResponse> getTopCourse() {
        List<TopCourseResponse> lst = new ArrayList<>();

        List<Map<String, Object>> courseList = courseService.getTop5Course();
        for(Map<String, Object> map : courseList) {
            lst.add(new TopCourseResponse((String) map.get("title"), ((BigInteger)map.get("students")).longValue()));
        }

        return lst;
    }

    @Override
    public LinkedList<DailyNewUserResponse> getDailyNewUser() {
        LinkedList<DailyNewUserResponse> lst;

        lst = dailyNewUserService.getDailyNewUser();

        return lst;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
