package com.kohhx.Spring.Security.Basic.Auth.MySQL.repository;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
        Optional<User>  findByUsernameOrEmail(String username, String email);

        boolean existsUserByEmail(String email);
}
