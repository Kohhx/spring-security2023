package com.kohhx.Spring.Security.Basic.Auth.MySQL.bootstrap;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.Role;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.entity.User;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.RoleRepository;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApplicationCommandLineRunner(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Add in Roles
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        roles.add(new Role("ADMIN"));
        roleRepository.saveAll(roles);

        Role user = roleRepository.findRoleByName("USER");
        Role admin = roleRepository.findRoleByName("ADMIN");
        System.out.println(user.getName());

        User kohhx = new User("kohhx", "password", "kohhxx6@gmail.com", List.of(user,admin));
        userRepository.save(kohhx);

        Optional<User> userFound = userRepository.findByUsernameOrEmail("kohhx", "kohhx");
        System.out.println(userFound.get().getUsername());
        System.out.println(userFound.get().getRolesList());

    }
}
