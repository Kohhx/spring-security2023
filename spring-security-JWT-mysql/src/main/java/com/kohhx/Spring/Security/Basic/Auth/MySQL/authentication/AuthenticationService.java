package com.kohhx.Spring.Security.Basic.Auth.MySQL.authentication;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO.UserRegistration;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.DuplicateResourceException;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.Role;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.User;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.RoleRepository;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistration userRegistration) throws DuplicateResourceException {
        if (userRepository.existsUserByEmail(userRegistration.email())){
            throw new DuplicateResourceException("Email already exist!");
        }
        User user = new User(userRegistration.username(),
                passwordEncoder.encode(userRegistration.password()),
                userRegistration.email());
        Arrays.stream(userRegistration.roles()).forEach(role -> {
            Role roleFound = roleRepository.findRoleByName(role);
            user.addRole(roleFound);
        });

        User userSaved = userRepository.save(user);
        System.out.println(userSaved.getRolesList());
        return userSaved;
    }
}
