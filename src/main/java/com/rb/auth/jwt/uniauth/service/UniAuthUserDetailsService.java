package com.rb.auth.jwt.uniauth.service;

import com.rb.auth.jwt.uniauth.repository.UniAuthUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UniAuthUserDetailsService implements UserDetailsService {

    private UniAuthUsersRepository uniAuthUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return uniAuthUsersRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND IN DB!"));
    }
}
