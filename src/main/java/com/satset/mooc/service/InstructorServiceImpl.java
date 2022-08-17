package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.InstructorDashboard;
import com.satset.mooc.repository.InstructorDashboardRepository;
import com.satset.mooc.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    InstructorDashboardRepository instructorDashboardRepository;
    @Autowired
    DailyNewUserService dailyNewUserService;

    @Override
    public Instructor registerInstructor(String name, String gender, String image, String email, String password) {
        dailyNewUserService.setDailyNewUser(false, true);
        return instructorRepository.save(new Instructor(name, gender, image, email, password));
    }

    @Override
    public Boolean instructorExist(String email) {
        return instructorRepository.findByEmail(email).isPresent();
    }

    @Override
    public Instructor getInstructorById(Long user_id) {
        return instructorRepository.findById(user_id).orElse(null);
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Boolean setInstructorStatus(Instructor instructor, String status) {
        if(!instructor.getVerified_status().equalsIgnoreCase("Approved")) {
            if(status.equalsIgnoreCase("Rejected")) {
                dailyNewUserService.setDailyNewUser(false, false);
                delete(instructor);
                return true;
            }
            instructor.setVerified_status(status);
            save(instructor);
            createDashboard(instructor);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Instructor instructor) {
        instructorRepository.delete(instructor);
    }

    @Override
    public void createDashboard(Instructor instructor) {
        instructorDashboardRepository.save(new InstructorDashboard(instructor.getId()));
    }

    @Override
    public InstructorDashboard getInstructorDashboardById(long id) {
        return instructorDashboardRepository.findById(id).orElse(null);
    }

    @Override
    public void updateDashboard(Instructor instructor, String courseOldStatus, String courseNewStatus) {
        InstructorDashboard instructorDashboard = instructorDashboardRepository.findById(instructor.getId()).orElse(null);
        if(instructorDashboard==null) return;

        if(courseOldStatus.equalsIgnoreCase("Pending"))
            instructorDashboard.setPending_course(instructorDashboard.getPending_course()-1);
        else if(courseOldStatus.equalsIgnoreCase("Rejected"))
            instructorDashboard.setRejected_course(instructorDashboard.getRejected_course()-1);

        if(courseNewStatus.equalsIgnoreCase("Approved"))
            instructorDashboard.setVerified_course(instructorDashboard.getVerified_course()+1);
        else if(courseNewStatus.equalsIgnoreCase("Rejected"))
            instructorDashboard.setRejected_course(instructorDashboard.getRejected_course()+1);
    }

    @Override
    public Boolean addPendingCourseToDashboard(Instructor instructor) {
        InstructorDashboard instructorDashboard = instructorDashboardRepository.findById(instructor.getId()).orElse(null);
        if(instructorDashboard==null) return false;
        instructorDashboard.setCreated_course(instructorDashboard.getCreated_course()+1);
        instructorDashboard.setPending_course(instructorDashboard.getPending_course()+1);
        return true;
    }

    @Override
    public void save(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    public void addAndSaveCourse(Instructor instructor, Course course) {
        instructor.addCourseOwned(course);
        save(instructor);
        addPendingCourseToDashboard(instructor);
    }

    @Override
    public Boolean quizEligibilityCheck(Instructor instructor, Quiz quiz) {
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(quiz.getCourse()));
    }

    @Override
    public Boolean lectureEligibilityViaCourseCheck(Instructor instructor, Course course) {
        if(course==null) return false;
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(course));
    }

    @Override
    public Boolean lectureEligibilityCheck(Instructor instructor, Lecture oldLecture) {
        if(oldLecture==null) return false;
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(oldLecture.getCourse()));
    }

    @Override
    public Boolean quizEligibilityViaCourseCheck(Instructor instructor, Course course) {
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(course));
    }

    @Override
    public Boolean questionEligibilityCheck(Instructor instructor, Question question) {
        return !Boolean.FALSE.equals(instructor.getCourseOwned().contains(question.getQuiz().getCourse()));
    }

    @Override
    public Boolean isValidated(Instructor instructor) {
        if(instructor.getVerified_status().equalsIgnoreCase("Approved")) return true;
        return  false;
    }

    @Override
    public long countAll() {
        return instructorRepository.count();
    }
}
