package com.rb.auth.jwt.uniauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UniAuthLoginResponse {
    private String username;
    private String token;
    private String roles;
    private String refreshToken;
}
