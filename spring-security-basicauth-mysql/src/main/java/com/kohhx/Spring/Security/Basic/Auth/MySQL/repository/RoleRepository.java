package com.kohhx.Spring.Security.Basic.Auth.MySQL.repository;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);

}
