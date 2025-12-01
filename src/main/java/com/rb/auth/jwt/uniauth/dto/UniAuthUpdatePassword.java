package com.rb.auth.jwt.uniauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UniAuthUpdatePassword {

    @NotBlank
    private String existingPassword;
    @NotBlank
    private String newPassword;
}
