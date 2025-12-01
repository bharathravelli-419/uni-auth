package com.rb.auth.jwt.uniauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UniAuthRefreshTokenRequest {
    @NotBlank(message = "refreshToken cannot be Blank or Null")
    private String refreshToken;
}
