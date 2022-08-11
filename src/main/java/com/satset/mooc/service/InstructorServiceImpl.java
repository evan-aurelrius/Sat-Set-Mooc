package com.satset.mooc.service;

import com.satset.mooc.model.Instructor;
import com.satset.mooc.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Instructor getInstructorById(Long user_id) {
        return instructorRepository.findById(user_id).orElse(null);
    }

    @Override
    public void setInstuctorStatus(Instructor instructor, String status) {
        instructor.setVerified_status(status);
        instructorRepository.save(instructor);
    }
}
