package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.authentication.RegisterRequest;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.dto.user.UserCreateRequest;
import com.hcmute.baloshop.dto.user.UserRequestDTO;
import com.hcmute.baloshop.entities.Role;
import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {


  ResponseEntity<ResponseObject> getAllUser();
  ResponseEntity<ResponseObject> updateUserInfoById(UserRequestDTO userRequestDTO);

  ResponseEntity<?> register(RegisterRequest request);
  ResponseEntity<ResponseObject> updateStatus(Long userId);
  ResponseEntity<ResponseObject> getById(Long userId);
  ResponseEntity<ResponseObject> createNewAccount(UserCreateRequest userCreateRequest);
  ResponseEntity<ResponseObject> getAllAdmin(Long id);
}
