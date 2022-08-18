package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.model.Instructor;
import com.satset.mooc.repository.AdminRepository;
import com.satset.mooc.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public void sendMail(String to, String subject, String messages) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messages);
        message.setFrom("SatSet");
        sender.send(message);
    }

    public void sendMail(String[] to, String subject, String messages){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messages);
        message.setFrom("SatSet");
        sender.send(message);
    }

    @Override
    public void sendMailToAllAdmin(String subject, String messages) {
        String[] emails = adminRepository.findAll().stream()
                .map(Admin::getEmail).toArray(String[]::new);
        sendMail(emails, subject, messages);
    }

}
