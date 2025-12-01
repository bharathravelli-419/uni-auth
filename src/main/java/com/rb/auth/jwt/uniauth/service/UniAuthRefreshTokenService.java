package com.rb.auth.jwt.uniauth.service;

import com.rb.auth.jwt.uniauth.entity.UniAuthRefreshToken;
import com.rb.auth.jwt.uniauth.entity.UniAuthUser;
import com.rb.auth.jwt.uniauth.repository.UniAuthRefreshTokenRepository;
import com.rb.auth.jwt.uniauth.repository.UniAuthUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UniAuthRefreshTokenService {

    private UniAuthRefreshTokenRepository uniAuthRefreshTokenRepository;
    private UniAuthUsersRepository uniAuthUsersRepository;

    public UniAuthRefreshToken isRefreshTokenValid(String token){
            UniAuthRefreshToken uniAuthRefreshToken=uniAuthRefreshTokenRepository.findByToken(token).orElseThrow(()-> new NoSuchElementException("REFRESH TOKEN WAS NOT FOUND"));
            return uniAuthRefreshToken.getExpiryDate().compareTo(Instant.now()) > 0 ? uniAuthRefreshToken : null;
    }


    public UniAuthRefreshToken createRefreshToken(UniAuthUser uniAuthUser){

        UniAuthRefreshToken uniAuthRefreshToken;
        uniAuthRefreshToken = uniAuthRefreshTokenRepository.findByUsername(uniAuthUser.getUsername()).orElse( new UniAuthRefreshToken());
         uniAuthRefreshToken.setUniAuthUser(uniAuthUsersRepository.findByUsername(uniAuthUser.getUsername()).isPresent()?
                        uniAuthUsersRepository.findByUsername(uniAuthUser.getUsername()).get(): uniAuthUser);
          uniAuthRefreshToken.setToken(UUID.randomUUID().toString());
        uniAuthRefreshToken.setExpiryDate(Instant.now().plusMillis(86400000));

        return uniAuthRefreshTokenRepository.save(uniAuthRefreshToken);
    }

}
