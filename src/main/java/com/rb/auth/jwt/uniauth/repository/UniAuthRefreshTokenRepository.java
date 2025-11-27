package com.rb.auth.jwt.uniauth.repository;

import com.rb.auth.jwt.uniauth.entity.UniAuthRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UniAuthRefreshTokenRepository extends JpaRepository<UniAuthRefreshToken, Long> {
    Optional<UniAuthRefreshToken> findByToken(String token);
    @Query("SELECT urt FROM UniAuthRefreshToken urt JOIN urt.uniAuthUser u WHERE u.username = :username")
    Optional<UniAuthRefreshToken> findByUsername(String username);
}
