package com.ploging.plog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailMessage {

  private String to;
  private String subject;
  private String message;

}
