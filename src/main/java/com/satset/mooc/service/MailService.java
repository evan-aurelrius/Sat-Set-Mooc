package com.satset.mooc.service;

public interface MailService {

    void sendMail(String to, String subject, String messages);

    void sendMail(String[] to, String subject, String messages);

    void sendMailToAllAdmin(String subject, String messages);

}
