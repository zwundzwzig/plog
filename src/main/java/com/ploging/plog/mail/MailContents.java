package com.ploging.plog.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailContents {

  private String receiver;
  private String title;
  private String  contents;

}
