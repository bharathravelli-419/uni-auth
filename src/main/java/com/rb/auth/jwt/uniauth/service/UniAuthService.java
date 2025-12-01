package com.rb.auth.jwt.uniauth.service;

import com.rb.auth.jwt.uniauth.dto.UniAuthLogin;
import com.rb.auth.jwt.uniauth.dto.UniAuthSignup;
import com.rb.auth.jwt.uniauth.entity.UniAuthUser;
import com.rb.auth.jwt.uniauth.repository.UniAuthUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UniAuthService {
    private UniAuthUsersRepository uniAuthUsersRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public UniAuthUser saveUser(UniAuthSignup uniAuthSignup){
        UniAuthUser uniAuthUser = UniAuthUser.builder()
                .username(uniAuthSignup.getUsername())
                .password(passwordEncoder.encode(uniAuthSignup.getPassword()))
                .email(uniAuthSignup.getEmail())
                .roles(uniAuthSignup.getRoles())
                .build();
        return uniAuthUsersRepository.save(uniAuthUser);
    }

    public UniAuthUser authenticateUser(UniAuthLogin uniAuthLogin){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(uniAuthLogin.getUsername(), uniAuthLogin.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return uniAuthUsersRepository.findByUsername(uniAuthLogin.getUsername()).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }

    public boolean updateUserPassword(String existingPassword, String newPassword){
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UniAuthUser uniAuthUser = uniAuthUsersRepository.findByUsername(username).get();
            if(passwordEncoder.matches(existingPassword, uniAuthUser.getPassword())){
                uniAuthUser.setPassword(passwordEncoder.encode(newPassword));
                uniAuthUsersRepository.save(uniAuthUser);
                return true;
            }
        }
        catch (Exception e){
            throw new RuntimeException("ERROR UPDATING PASSWORD");
        }
        return false;
    }

    public UniAuthUser getUserByUsername(String username){
        return uniAuthUsersRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }


}
