package com.hcmute.baloshop.dto.authentication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private Long id;
  private String accessToken;
  private Boolean enable;
//  private String refreshToken;
}