package com.satset.mooc.service;

import com.satset.mooc.model.*;
import com.satset.mooc.model.InstructorDashboard;
import com.satset.mooc.model.response.InstructorProposalResponse;
import com.satset.mooc.repository.InstructorDashboardRepository;
import com.satset.mooc.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    InstructorDashboardRepository instructorDashboardRepository;
    @Autowired
    DailyNewUserService dailyNewUserService;
    @Autowired
    MailService mailService;

    private static final String VERIFICATION = "Instructor Verification";
    private static final String APPROVED = "Approved";
    private static final String REJECTED = "Rejected";

    @Override
    public Instructor registerInstructor(String name, String gender, String image, String email, String password) {
        dailyNewUserService.setDailyNewUser(false, true);
        mailService.sendMailToAllAdmin(VERIFICATION, "Hello, \n\nNew Instructor with the name "+name+" waiting for verification \n\nThanks.");
        Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
        logger.warn("Done");
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
        if(!instructor.getVerified_status().equalsIgnoreCase(APPROVED)) {
            if(status.equalsIgnoreCase(REJECTED)) {
                dailyNewUserService.setDailyNewUser(false, false);
                mailService.sendMail(instructor.getEmail(), VERIFICATION, "Hello, \n\nSorry, your account registration was not approved by the admin \n\nThanks.");
                delete(instructor);
                return true;
            }
            instructor.setVerified_status(status);
            save(instructor);
            createDashboard(instructor);
            mailService.sendMail(instructor.getEmail(), VERIFICATION, "Hello, \n\nCongratulations, your account registration has been approved \n\nThanks.");
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
        else if(courseOldStatus.equalsIgnoreCase(REJECTED))
            instructorDashboard.setRejected_course(instructorDashboard.getRejected_course()-1);

        if(courseNewStatus.equalsIgnoreCase(APPROVED))
            instructorDashboard.setVerified_course(instructorDashboard.getVerified_course()+1);
        else if(courseNewStatus.equalsIgnoreCase(REJECTED))
            instructorDashboard.setRejected_course(instructorDashboard.getRejected_course()+1);
    }

    @Override
    public void addPendingCourseToDashboard(Instructor instructor) {
        InstructorDashboard instructorDashboard = instructorDashboardRepository.findById(instructor.getId()).orElse(null);
        if(instructorDashboard==null) return;
        instructorDashboard.setCreated_course(instructorDashboard.getCreated_course()+1);
        instructorDashboard.setPending_course(instructorDashboard.getPending_course()+1);
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
        return instructor.getVerified_status().equalsIgnoreCase(APPROVED);
    }

    @Override
    public long countAll() {
        return instructorRepository.count();
    }

    @Override
    public Page<Instructor> getInstructorProposalPage(int page) {
        Pageable pageable = PageRequest.of(page-1,10);
        return instructorRepository.findAllByVerified_status(pageable);
    }

    @Override
    public List<InstructorProposalResponse> convertToList(Page<Instructor> instructorPage) {
        List<Instructor> rawLst = instructorPage.toList();
        List<InstructorProposalResponse> lst = new ArrayList<>();
        for(Instructor i:rawLst) {
            lst.add(new InstructorProposalResponse(i.getId(), i.getName(), i.getGender(), i.getImage()));
        }
        return lst;
    }
}
