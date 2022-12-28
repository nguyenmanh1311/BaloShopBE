package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.authentication.AuthenticationRequest;
import com.hcmute.baloshop.dto.authentication.AuthenticationResponse;
import com.hcmute.baloshop.dto.authentication.RegisterRequest;
import com.hcmute.baloshop.dto.user.UserChangePasswordDTO;
import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.AuthenticationService;
import com.hcmute.baloshop.services.UserService;
import com.hcmute.baloshop.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private JwtTokenUtil jwtUtil;

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<?> register(RegisterRequest request) {
    return this.userService.register(request);
  }

  @Override
  public ResponseEntity<?> login(AuthenticationRequest request) {
    try {
      log.info("Phone: " + request.getPhone() + " login...");
      Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
      User user = (User) authentication.getPrincipal();
      String accessToken = jwtUtil.generateAccessToken(user);
      AuthenticationResponse response = new AuthenticationResponse(user.getId(), accessToken,user.getEnable());
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Login Successfully", response));
  } catch (BadCredentialsException exception) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    } catch (AuthenticationException exception) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.MULTI_STATUS, exception.getMessage()));
    }
  }

  @Override
  public ResponseEntity<?> changePassword(UserChangePasswordDTO userChangePasswordDTO) {
    Optional<User> user=this.userRepository.findById(userChangePasswordDTO.getUserId());
    if (!user.isPresent())
      throw new ResourceNotFoundException("User not found");
    System.out.println(passwordEncoder.encode(userChangePasswordDTO.getOldPassword()));
    System.out.println(user.get().getPassword());
    System.out.println(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
    boolean check =passwordEncoder.matches(userChangePasswordDTO.getOldPassword(),user.get().getPassword());
    if(!check)
     throw new ResourceNotFoundException("Old password not correct");
    User newUser=user.get();
    newUser.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
    this.userRepository.save(newUser);
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseObject(HttpStatus.OK, "Update password successfully!", newUser));
  }
}