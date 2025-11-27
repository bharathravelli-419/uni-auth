package com.rb.auth.jwt.uniauth.dto;

import lombok.Data;

@Data
public class UniAuthSignup {
    private String username;
    private String password;
    private String email;
    private String roles;
}
