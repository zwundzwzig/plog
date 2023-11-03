package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.user.SignInRequest;
import com.sokuri.plog.domain.dto.user.SignInResponse;
import com.sokuri.plog.domain.dto.user.UserCheckResponse;
import com.sokuri.plog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1.0/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(summary = "유저 이메일 중복  체크")
  @GetMapping("/checkMail/{email}")
  public ResponseEntity<UserCheckResponse> checkEmail(@PathVariable @Valid String email) {
    UserCheckResponse response = userService.isEmailExist(email);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "유저 닉네임 중복  체크")
  @GetMapping("/checkNickname/{nickname}")
  public ResponseEntity<UserCheckResponse> checkNickname(@PathVariable String nickname) {
    UserCheckResponse response = userService.isNicknameExists(nickname);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "회원가입")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping("/signIn")
  public ResponseEntity<SignInResponse> postUserSignIn(
          @RequestPart(value = "image", required = false) MultipartFile file,
          @RequestPart("request") SignInRequest request
  ) {
    request.setProfileImage(file);
    SignInResponse response = userService.signIn(request);

    return ResponseEntity.ok(response);
  }
}
