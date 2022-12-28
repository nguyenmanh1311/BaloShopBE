package com.hcmute.baloshop.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @Size(message = "Invalid first name size must be larger 1 and less than 30", max = 30, min = 1)
  private String fullName;

  @Size(message = "Invalid email size.", max = 320, min = 10)
  @NotNull(message = "An email is required!")
  @Pattern(
          regexp = ("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"),
          message = "Invalid email does not match pattern!")
  private String email;

  @Size(message = "Invalid phone size.", max = 12, min = 9)
  private String phone;

  @Size(message = "Invalid password size.", max = 30, min = 6)
  private String password;

  @Size(message = "Invalid password size.", max = 30, min = 6)
  private String confirmPassword;

}
