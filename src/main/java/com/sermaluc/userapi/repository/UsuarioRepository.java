package com.sermaluc.userapi.repository;

import com.sermaluc.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
