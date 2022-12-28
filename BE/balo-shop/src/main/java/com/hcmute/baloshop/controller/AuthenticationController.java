package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.authentication.AuthenticationRequest;
import com.hcmute.baloshop.dto.authentication.RegisterRequest;
import com.hcmute.baloshop.dto.user.UserChangePasswordDTO;
import com.hcmute.baloshop.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request) {
    return this.authenticationService.login(request);
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
    return this.authenticationService.register(request);
  }
  @PostMapping("change-password")
  public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO){
    return this.authenticationService.changePassword(userChangePasswordDTO);
  }
}
