package com.sokuri.plog.service;

import com.sokuri.plog.domain.MailMessage;

public interface MailService {

  String sendMail(MailMessage mailMessage, String type);

}
