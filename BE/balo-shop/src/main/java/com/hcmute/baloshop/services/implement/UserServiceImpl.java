package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.authentication.RegisterRequest;
import com.hcmute.baloshop.dto.user.UserCreateRequest;
import com.hcmute.baloshop.dto.user.UserRequestDTO;
import com.hcmute.baloshop.dto.user.UserResponseDTO;
import com.hcmute.baloshop.entities.*;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.AddressRepository;
import com.hcmute.baloshop.repositories.RoleRepository;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.UserService;
import com.hcmute.baloshop.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllUser() {
        List<User> users = this.userRepository.findAllCustomer();
        List<UserResponseDTO> dtoList = new ArrayList<>();
        for (User item : users) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(item.getId());
            userResponseDTO.setRole(item.getRole());
            userResponseDTO.setFullName(item.getFullName());
            userResponseDTO.setGender(item.getGender());
            userResponseDTO.setDateOfBirth(item.getDateOfBirth().toString());
            userResponseDTO.setPhone(item.getPhone());
            userResponseDTO.setEmail(item.getEmail());
            userResponseDTO.setPhoto(item.getPhoto());
            userResponseDTO.setEnable(item.getEnable());
            Optional<Address> address=this.addressRepository.findTopByUserId(item.getId());
            if (address.isPresent()) {
                String stringaddress = address.get().getAddressLine() + " " + address.get().getWard() + " " + address.get().getDistrict() + " " + address.get().getProvince();
                userResponseDTO.setAddress(stringaddress);
            }
            dtoList.add(userResponseDTO);

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get all user successfully!!!", dtoList));
    }
    @Override
    public ResponseEntity<ResponseObject> updateUserInfoById(UserRequestDTO userRequestDTO) {
        Optional<User> user=this.userRepository.findById(userRequestDTO.getId());
        if(!user.isPresent())
            throw new ResourceNotFoundException("User not found");
        User userUpdate=user.get();
        userUpdate.setFullName(userRequestDTO.getFullName());
        userUpdate.setPhone(userUpdate.getPhone());
        userUpdate.setEmail(userUpdate.getEmail());
        this.userRepository.save(userUpdate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update user successfully!!!", userUpdate));
    }


    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        Boolean isEmailExist = isEmailExisted(request.getEmail());
        Boolean isPhoneNumber = isPhoneNumberExisted(request.getPhone());

        if(isPhoneNumber){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE, "Số điện thoại đã được sử dụng!"));
        }
        if(isEmailExist){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE, "Địa chỉ Email đã được sử dụng!"));
        }
        User user = new User();
        Role role = roleRepository.findByName(RoleEnum.ROLE_USER.name()).get();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnable(true);
        user.setEmail(request.getEmail());
        user.setRole(role);
        User save = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Register successfully!", save));
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatus(Long userId) {
        Optional<User> users=this.userRepository.findById(userId);
        if(!users.isPresent())
            throw new ResourceNotFoundException("User not found");
        User user=users.get();
        user.setEnable(!users.get().getEnable());
        this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update status successfully!", user));
    }

    @Override
    public ResponseEntity<ResponseObject> getById(Long userId) {
        Optional<User> user=this.userRepository.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Get user successfully!", user.get()));
    }

    @Override
    public ResponseEntity<ResponseObject> createNewAccount(UserCreateRequest userCreateRequest) {
        List<User> userfound=this.userRepository.validateUser(userCreateRequest.getEmail(), userCreateRequest.getPhone());
        if(!userfound.isEmpty())
            throw new ResourceNotFoundException("phone/email already existed");
        User user=new User();
        user.setGender(userCreateRequest.getGender());
        user.setEnable(true);
        user.setFullName(userCreateRequest.getFullName());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        Date parsed = null;
        try {
            parsed = format.parse(userCreateRequest.getDateOfBirth());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        user.setDateOfBirth(sql);
        user.setPhone(userCreateRequest.getPhone());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhoto(userCreateRequest.getPhoto());
        user.setPassword(passwordEncoder.encode("123456a@"));
        Role role = roleRepository.findByName(RoleEnum.ROLE_ADMIN.name()).get();
        user.setRole(role);
        this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Create user successfully!", user));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllAdmin(Long id) {

        List<User> users = this.userRepository.findAllAdmin(id);
        List<UserResponseDTO> dtoList = new ArrayList<>();
        for (User item : users) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(item.getId());
            userResponseDTO.setRole(item.getRole());
            userResponseDTO.setFullName(item.getFullName());
            userResponseDTO.setGender(item.getGender());
            userResponseDTO.setDateOfBirth(item.getDateOfBirth().toString());
            userResponseDTO.setPhone(item.getPhone());
            userResponseDTO.setEmail(item.getEmail());
            userResponseDTO.setPhoto(item.getPhoto());
            userResponseDTO.setEnable(item.getEnable());
            Optional<Address> address=this.addressRepository.findTopByUserId(item.getId());
            if (address.isPresent()) {
                String stringaddress = address.get().getAddressLine() + " " + address.get().getWard() + " " + address.get().getDistrict() + " " + address.get().getProvince();
                userResponseDTO.setAddress(stringaddress);
            }
            dtoList.add(userResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get all user successfully!!!", dtoList));
    }

    private Boolean isPhoneNumberExisted(String phone) {
        Optional<User> userByPhone = this.userRepository.findByPhone(phone);
        if (userByPhone.isPresent()) {
            return true;
        }
        return false;
    }
    private Boolean isEmailExisted(String email) {
        Optional<User> userByEmail = this.userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            return true;
        }
        return false;
    }


}
