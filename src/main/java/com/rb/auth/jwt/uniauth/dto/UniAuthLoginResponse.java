package com.rb.auth.jwt.uniauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UniAuthLoginResponse {
    @NotNull
    private String username;
    @NotBlank
    private String token;
    @NotNull
    private String roles;
    @NotBlank
    private String refreshToken;
}
