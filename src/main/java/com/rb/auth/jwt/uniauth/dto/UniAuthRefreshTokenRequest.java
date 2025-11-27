package com.rb.auth.jwt.uniauth.dto;

import lombok.Data;

@Data
public class UniAuthRefreshTokenRequest {
    private String refreshToken;
}
