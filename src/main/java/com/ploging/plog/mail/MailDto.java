package com.ploging.plog.mail;

import com.ploging.plog.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {

    private String address;
    private String title;
    private String content;
    private User sender;
    private User receiver;

}
