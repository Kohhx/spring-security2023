package com.kohhx.Spring.Security.Basic.Auth.MySQL.authentication;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO.AuthRequest;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO.UserRegistration;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.DuplicateResourceException;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
//@RequestMapping(path="api/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody UserRegistration userRegistration) {
        System.out.println(userRegistration);
        System.out.println(Arrays.toString(userRegistration.roles()));
            try {
                var message = authenticationService.registerUser(userRegistration);
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            } catch (DuplicateResourceException e) {
                throw new RuntimeException(e);
            }
    }


    @PostMapping("/api/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
       
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    // This route require basic auth, Both USER AND ADMIN can login
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    // This route require basic auth, Only ADMIN can access
    @GetMapping("/message")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getMessage() {
        return "Hey, Admin";
    }


}
