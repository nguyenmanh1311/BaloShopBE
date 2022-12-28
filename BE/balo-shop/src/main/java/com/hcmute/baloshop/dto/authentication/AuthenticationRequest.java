package com.hcmute.baloshop.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  @Size(message = "Invalid number phone", max = 320, min = 10)
  @NotNull(message = "Phone number required")
  private String phone;

  @Length(message = "Invalid password size.", min = 6, max = 16)
  @NotNull(message = "An password is required!")
  private String password;
}
