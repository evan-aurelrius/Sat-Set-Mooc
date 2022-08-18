package com.satset.mooc.service;

import com.satset.mooc.model.Admin;

import java.util.List;

public interface MailService {

    void sendMail(String to, String subject, String messages);

    void sendAdminVerifyInstructor(String subject, String messages);
}
