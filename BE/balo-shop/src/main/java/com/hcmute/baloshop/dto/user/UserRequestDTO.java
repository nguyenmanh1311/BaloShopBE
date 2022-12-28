package com.hcmute.baloshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String addressLine;
    private String ward;
    private String district;
    private String province;
}
