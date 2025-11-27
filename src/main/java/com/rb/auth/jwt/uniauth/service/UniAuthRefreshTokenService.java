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


    public UniAuthRefreshToken createRefreshToken(String username){

        UniAuthRefreshToken uniAuthRefreshToken;
        uniAuthRefreshToken = uniAuthRefreshTokenRepository.findByUsername(username).orElseThrow();

         uniAuthRefreshToken.setUniAuthUser(uniAuthUsersRepository.findByUsername(username).isPresent()?
                        uniAuthUsersRepository.findByUsername(username).get() : null);
          uniAuthRefreshToken.setToken(UUID.randomUUID().toString());
        uniAuthRefreshToken.setExpiryDate(Instant.now().plusMillis(86400000));

        return uniAuthRefreshTokenRepository.save(uniAuthRefreshToken);
    }

}
