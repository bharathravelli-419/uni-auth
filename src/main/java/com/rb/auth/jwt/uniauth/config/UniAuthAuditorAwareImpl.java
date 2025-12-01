package com.rb.auth.jwt.uniauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
@Slf4j
public class UniAuthAuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Getting current auditor: " + (authentication != null ? authentication.getName() : ""));
        if(authentication != null){
            return Optional.of(authentication.getName());

        }
        return Optional.empty();
    }
}
