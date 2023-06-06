package com.kohhx.Spring.Security.Basic.Auth.MySQL.bootstrap;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.Role;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.User;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.RoleRepository;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationCommandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Add in Roles
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        roles.add(new Role("ROLE_ADMIN"));
        roleRepository.saveAll(roles);

        // Retreieve Roles to add to USERS
        Role user = roleRepository.findRoleByName("ROLE_USER");
        Role admin = roleRepository.findRoleByName("ROLE_ADMIN");
        System.out.println(user.getName());

        // Create and Add users with different roles
        User kohhx = new User("kohhx", passwordEncoder.encode("password"), "kohhxx6@gmail.com", List.of(user,admin));
        User leon = new User("leon", passwordEncoder.encode("password"), "leon@gmail.com");
        leon.addRole(user);
        userRepository.save(kohhx);
        userRepository.save(leon);


        // Find user by username or email
        Optional<User> userFound = userRepository.findByUsernameOrEmail("kohhx", "kohhx");
        System.out.println(userFound.get().getUsername());
        System.out.println(userFound.get().getRolesList());

    }
}
