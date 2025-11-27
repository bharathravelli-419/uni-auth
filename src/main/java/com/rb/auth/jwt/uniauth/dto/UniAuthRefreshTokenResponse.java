package com.rb.auth.jwt.uniauth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UniAuthRefreshTokenResponse {
    String newToken;
    String refreshToken;
}
