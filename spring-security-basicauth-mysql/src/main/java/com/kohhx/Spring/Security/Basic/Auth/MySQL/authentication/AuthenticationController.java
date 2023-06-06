package com.kohhx.Spring.Security.Basic.Auth.MySQL.authentication;

import com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO.AuthenticationResponse;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO.UserRegistration;
import com.kohhx.Spring.Security.Basic.Auth.MySQL.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
//@RequestMapping(path="api/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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

    // This route require basic auth, Both USER AND ADMIN can login
    @GetMapping("/api/login")
    public ResponseEntity<AuthenticationResponse> login() {
        return new ResponseEntity<>(new AuthenticationResponse("Login Success"), HttpStatus.OK);
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
