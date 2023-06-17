package com.ploging.plog.mail;

public interface MailService {

  String sendMail(MailMessage mailMessage, String type);

}
