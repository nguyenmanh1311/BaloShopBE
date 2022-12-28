package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.authentication.AuthenticationRequest;
import com.hcmute.baloshop.dto.authentication.RegisterRequest;
import com.hcmute.baloshop.dto.user.UserChangePasswordDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
  ResponseEntity<?> register(RegisterRequest request);

  ResponseEntity<?> login(AuthenticationRequest request);
  ResponseEntity<?> changePassword(UserChangePasswordDTO userChangePasswordDTO);
}
