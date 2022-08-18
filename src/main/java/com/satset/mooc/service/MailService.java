package com.satset.mooc.service;

import com.satset.mooc.model.Admin;

import java.util.List;

public interface MailService {

    void sendMail(String to, String subject, String messages);

    void sendMail(String[] to, String subject, String messages);

    void sendMailToAllAdmin(String subject, String messages);

}
