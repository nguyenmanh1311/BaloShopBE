package com.hcmute.baloshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDTO {
    public Long userId;
    private String oldPassword;
    private String newPassword;
}
