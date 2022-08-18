package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.DailyNewUser;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.repository.AdminRepository;
import com.satset.mooc.repository.DailyNewUserRepository;
import com.satset.mooc.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitializeSqlDataServiceImpl implements InitializeSqlDataService{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DailyNewUserRepository dailyNewUserRepository;
    @Autowired
    StudentService studentService;


    @Override
    public void quickInitialize() {
        if(adminRepository.count()==0) {
            initializeDailyNewUser();
            initializeAdmin();
            initializeInstructor();
            initializeStudent();
        }
    }

    private void initializeDailyNewUser() {
        List<DailyNewUser> lst = new ArrayList<>();
        for(int i=6; i>=0; i--)
            lst.add(new DailyNewUser(new Date(System.currentTimeMillis()- (long) i * 24 * 60 * 60 * 1000)));
        dailyNewUserRepository.saveAll(lst);
    }

    private void initializeAdmin() {
        Admin admin1 = new Admin("Administrator","admin1@localhost",passwordEncoder.encode("admin123"));
        adminRepository.save(admin1);
    }

    private void initializeInstructor() {
        Instructor instructor1 = new Instructor("Jack","m","profile1.jpeg","jacktheripper@gmail.com", passwordEncoder.encode("jack123"));
        instructorRepository.save(instructor1);
    }

    private void initializeStudent() {
        studentService.registerStudent("Timmy","m","profile2.jpg","timmyadventure@gmail.com",passwordEncoder.encode("timmy123"));
    }
}
