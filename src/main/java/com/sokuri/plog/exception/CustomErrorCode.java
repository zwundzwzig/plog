package com.sokuri.plog.exception;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
  /**
   * 1000 : 요청 성공
   */
  SUCCESS(true, 1000, "요청에 성공하였습니다."),

  /**
   * 2000 : Request 오류
   */
  // Common
  REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
  EMPTY_ACCESS_JWT(false, 2001, "Access 토큰을 입력해주세요."),
  EMPTY_REFRESH_JWT(false, 2002, "Refresh 토큰을 입력해주세요."),
  WRONG_TYPE_TOKEN(false, 2003, "잘못된 토큰 입니다."),
  UNSUPPORTED_TOKEN(false, 2004, "지원되지 않는 토큰 입니다."),
  NOT_EXIST_REFRESH_TOKEN(false,2005,"존재하지 않거나 만료된 Refresh 토큰입니다. 다시 로그인해주세요."),
  EXPIRED_TOKEN(false,2006,"만료된 Access 토큰입니다. Refresh 토큰을 이용해서 새로운 Access 토큰을 발급 받으세요."),
  INDEX_NOT_FOUND(false, 2007, "인덱스가 존재하지 않습니다."),
  TOKEN_NOT_FOUND(false, 2008, "토큰이 존재하지 않습니다."),

  // users
  INVALID_USER_UUID(false, 2101, "유저 정보를 불러오는데 실패했습니다"),

  /**
   * 3000 : Response 오류
   */
  // Common
  RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

  /**
   * 4000 : 연결 오류
   */
  DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
  SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
  REDIS_ERROR(false, 4002, "redis 연결에 실패하였습니다."),

  /**
   * 5000 : 알 수 없는 오류
   */
  UNKNOWN_ERROR(false, 5002, "알 수 없는 에러가 발생했습니다.");

  private final boolean isSuccess;
  private final int code;
  private final String message;

  CustomErrorCode(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
    this.isSuccess = isSuccess;
    this.code = code;
    this.message = message;
  }

  public static CustomErrorCode of(final String errorName){
    return CustomErrorCode.valueOf(errorName);
  }
}
