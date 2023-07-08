package com.ploging.plog.service;

import com.ploging.plog.domain.MailMessage;

public interface MailService {

  String sendMail(MailMessage mailMessage, String type);

}
