package com.rb.auth.jwt.uniauth.repository;

import com.rb.auth.jwt.uniauth.entity.UniAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniAuthUsersRepository extends JpaRepository<UniAuthUser, Long> {
    Optional<UniAuthUser> findByUsername(String username);
}
