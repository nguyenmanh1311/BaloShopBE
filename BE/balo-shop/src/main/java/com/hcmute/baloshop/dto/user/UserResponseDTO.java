package com.hcmute.baloshop.dto.user;

import com.hcmute.baloshop.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private Role role;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String password;
    private String photo;
    private Boolean enable;
    private String address;
}
