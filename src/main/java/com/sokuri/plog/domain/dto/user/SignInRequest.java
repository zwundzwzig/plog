package com.sokuri.plog.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest {
  private String nickname;
  private String email;
  private String password;
  private LocalDate birthday;
  private MultipartFile profileImage;
  private String imageUrl;
}
