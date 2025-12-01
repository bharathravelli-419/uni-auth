package com.rb.auth.jwt.uniauth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UniAuthUserInfo {

    private String username;
    private String roles;
    private String email;
}
