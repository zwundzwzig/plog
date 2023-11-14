package com.sokuri.plog.domain.dto.user;

import com.sokuri.plog.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

  private String accessToken;
  private String refreshToken;

  public Token toEntity() {
    return Token.builder()
            .accessToken(this.accessToken)
            .refreshToken(this.refreshToken)
            .build();
  }
}
