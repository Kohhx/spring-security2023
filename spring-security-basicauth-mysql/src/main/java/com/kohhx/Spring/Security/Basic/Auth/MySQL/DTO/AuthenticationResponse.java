package com.kohhx.Spring.Security.Basic.Auth.MySQL.DTO;

public class AuthenticationResponse {
    private String message;

    public AuthenticationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
