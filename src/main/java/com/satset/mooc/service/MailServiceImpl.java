package com.satset.mooc.service;

import com.satset.mooc.model.Admin;
import com.satset.mooc.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    AdminRepository adminRepository;

    public void sendMail(String to, String subject, String messages){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messages);
        message.setFrom("SatSet");
        sender.send(message);
    }

    @Override
    public void sendAdminVerifyInstructor(String subject, String messages) {
        List<Admin> admins = adminRepository.findAll();
        for (Admin admin:admins){
            sendMail(admin.getEmail(), subject, messages);
        }
    }
}
