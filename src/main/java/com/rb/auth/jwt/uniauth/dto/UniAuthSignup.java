package com.rb.auth.jwt.uniauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class UniAuthSignup {
    @NotBlank(message = "username must not be blank")
    private String username;
    @NotBlank(message = "password must not be blank")
    private String password;
    @NotBlank(message = "email must not be blank")
    private String email;
    @NotBlank(message = "roles must not be blank")
    private String roles;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
    @PastOrPresent
    private Date createdDate;


}
