package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private final String jwt;
    String username;
    String orgCode;

    public AuthenticationResponse(String jwt,String username,String orgCode) {
        this.jwt = jwt;
        this.username = username;
        this.orgCode = orgCode;

    }
    public AuthenticationResponse(String jwt,String username, String orgCode){
        this.jwt = jwt;
        this.orgCode = orgCode;
        this.username=username;
    }
    public String getJwt() {
        return jwt;
    }
}
