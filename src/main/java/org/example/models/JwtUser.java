package org.example.models;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Entity
@Table(name = "jwt_user")
public class JwtUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="orgCode")
    private String orgCode;

    @Column(name="password")
    private String password;

    public JwtUser(){}

    public JwtUser(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
