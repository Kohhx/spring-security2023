package com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO;

public record UserRegistration(String username, String password, String email, String[] roles) {
}
