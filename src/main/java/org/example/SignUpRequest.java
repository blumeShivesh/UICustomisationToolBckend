package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    String username;
    String email;

    String password;
    String orgCode;
}
