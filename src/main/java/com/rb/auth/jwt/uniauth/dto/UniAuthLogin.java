package com.rb.auth.jwt.uniauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UniAuthLogin {
    @NotBlank(message = "username cannot be Blank or Null")
    String username;

    @NotBlank(message = "password cannot be Blank or Null")
    String password;
}
