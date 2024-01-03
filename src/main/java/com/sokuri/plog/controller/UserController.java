package com.sokuri.plog.controller;

import com.sokuri.plog.global.dto.user.*;
import com.sokuri.plog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1.0/user")
@RequiredArgsConstructor
@Tag(name = "유저 정보", description = "회원 API")
@Slf4j
public class UserController {
  private final UserService userService;

  @Operation(description = "임시적으로 필요", summary = "전체 유저 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<SignInResponse>> getUserList() {
    return ResponseEntity.ok(userService.getAllUserList());
  }

  @Operation(summary = "유저 이메일 중복  체크")
  @GetMapping("/checkMail/{email}")
  public ResponseEntity<UserCheckResponse> checkEmail(@PathVariable(value = "email") @Valid String email) {
    UserCheckResponse response = userService.isEmailExist(email);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "유저 닉네임 중복  체크")
  @GetMapping("/checkNickname/{nickname}")
  public ResponseEntity<UserCheckResponse> checkNickname(@PathVariable(value = "nickname") String nickname) {
    UserCheckResponse response = userService.isNicknameExists(nickname);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "회원가입")
  @ApiResponse(responseCode = "201", description = "Created User")
  @PostMapping("/sign-up")
  public ResponseEntity<SignInResponse> signUp(
          @RequestPart(value = "image", required = false) MultipartFile file,
          @Valid @RequestPart("request") SignUpRequest request
  ) {
    request.setProfileImage(file);
    SignInResponse response = userService.signUp(request);
    HttpHeaders headers = userService.signIn(SignInRequest.builder().email(response.getEmail()).build());

    return ResponseEntity.ok().headers(headers).body(response);
  }

  @Operation(summary = "로그인")
  @ApiResponse(responseCode = "200", description = "LogIn Success")
  @PostMapping("/sign-in")
  public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest request) {
    HttpHeaders headers = userService.signIn(request);
    return ResponseEntity.ok().headers(headers).build();
  }
}
