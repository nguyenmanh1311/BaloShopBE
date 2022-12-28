package com.hcmute.baloshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String fullName;
    private String gender;
    private String photo;
    private String dateOfBirth;
    private String phone;
    private String email;
}
