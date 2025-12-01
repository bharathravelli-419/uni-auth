package com.rb.auth.jwt.uniauth.controller;

import com.rb.auth.jwt.uniauth.dto.*;
import com.rb.auth.jwt.uniauth.dto.response.UniAuthUserInfo;
import com.rb.auth.jwt.uniauth.entity.UniAuthRefreshToken;
import com.rb.auth.jwt.uniauth.entity.UniAuthUser;
import com.rb.auth.jwt.uniauth.service.UniAuthJwtService;
import com.rb.auth.jwt.uniauth.service.UniAuthRefreshTokenService;
import com.rb.auth.jwt.uniauth.service.UniAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/uni-auth/v1")
@AllArgsConstructor
public class UniAuthController {

    private UniAuthService uniAuthService;
    private UniAuthJwtService uniAuthJwtService;
    private UniAuthRefreshTokenService uniAuthRefreshTokenService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Boolean> singUpNewUser(@RequestBody @Valid UniAuthSignup uniAuthSignup){
        UniAuthUser uniAuthUser = uniAuthService.saveUser(uniAuthSignup);
        return new ResponseEntity<>(true, null, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginNewUser(@RequestBody @Valid UniAuthLogin uniAuthLogin){
        UniAuthUser uniAuthUser= uniAuthService.authenticateUser(uniAuthLogin);
        String token = uniAuthJwtService.generateNewJwtToken(uniAuthUser.getUsername(), new HashMap<>());
        UniAuthRefreshToken refreshToken = uniAuthRefreshTokenService.createRefreshToken(uniAuthUser);
        return new ResponseEntity<>( UniAuthLoginResponse
                .builder()
                .username(uniAuthUser.getUsername())
                .roles(uniAuthUser.getRoles())
                .token(token)
                .refreshToken(refreshToken.getToken())
                .build()
                ,null, HttpStatus.OK);
    }

    @GetMapping("/temp")
    public ResponseEntity<?> getTempInfo(){
        return new ResponseEntity<>("temp info", null, HttpStatus.OK);
    }

    @GetMapping("/users/currentUserInfo")
    public ResponseEntity<UniAuthUserInfo> getCurrentUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UniAuthUser uniAuthUser = uniAuthService.getUserByUsername(username);
        UniAuthUserInfo uniAuthUserInfo = UniAuthUserInfo.builder()
                .username(uniAuthUser.getUsername())
                .email(uniAuthUser.getEmail())
                .roles(uniAuthUser.getRoles())
                .build();
        return new ResponseEntity<>(uniAuthUserInfo, null, HttpStatus.OK);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshJwtToken(@RequestBody UniAuthRefreshTokenRequest uniAuthRefreshTokenRequest){
        UniAuthRefreshToken uniAuthRefreshToken = uniAuthRefreshTokenService.isRefreshTokenValid(uniAuthRefreshTokenRequest.getRefreshToken());
        if(uniAuthRefreshToken != null){
            String token = uniAuthJwtService.generateNewJwtToken(uniAuthRefreshToken.getUniAuthUser().getUsername(), new HashMap<>());
            return new ResponseEntity<>(UniAuthRefreshTokenResponse.builder()
                    .newToken(token)
                    .refreshToken(uniAuthRefreshToken.getToken())
                    .build(), null, HttpStatus.OK);
        }
        return new ResponseEntity<>("INVALID REFRESH TOKEN",null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/users/update-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody @Valid UniAuthUpdatePassword uniAuthUpdatePassword){
        boolean isUpdated = uniAuthService.updateUserPassword(uniAuthUpdatePassword.getExistingPassword(), uniAuthUpdatePassword.getNewPassword());
        if(isUpdated){
            return new ResponseEntity<>("PASSWORD UPDATED SUCCESSFULLY", null, HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR UPDATING PASSWORD", null, HttpStatus.BAD_REQUEST);
    }

}
