package com.rb.auth.jwt.uniauth.controller;

import com.rb.auth.jwt.uniauth.dto.*;
import com.rb.auth.jwt.uniauth.entity.UniAuthRefreshToken;
import com.rb.auth.jwt.uniauth.entity.UniAuthUser;
import com.rb.auth.jwt.uniauth.service.UniAuthJwtService;
import com.rb.auth.jwt.uniauth.service.UniAuthRefreshTokenService;
import com.rb.auth.jwt.uniauth.service.UniAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> singUpNewUser(@RequestBody UniAuthSignup uniAuthSignup){
        UniAuthUser uniAuthUser = uniAuthService.saveUser(uniAuthSignup);
        return new ResponseEntity<>(true, null, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginNewUser(@RequestBody UniAuthLogin uniAuthLogin){
        UniAuthUser uniAuthUser= uniAuthService.authenticateUser(uniAuthLogin);
        String token = uniAuthJwtService.generateNewJwtToken(uniAuthUser.getUsername(), new HashMap<>());
        UniAuthRefreshToken refreshToken = uniAuthRefreshTokenService.createRefreshToken(uniAuthUser.getUsername());
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

    @GetMapping("/users/currentUser")
    public String getCurrentUserInfo() {
        return "current user";
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
}
