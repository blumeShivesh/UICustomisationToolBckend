package org.example.models;

public class AuthenticationRequest {
    public String email;
    public String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
