package org.example;

import io.jsonwebtoken.impl.AbstractTextCodec;
import org.example.MyUserDetailsService.MyUserDetailsService;
import org.example.models.*;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.example.util.jwtUtil;
import org.example.SignUpRequest;
import org.example.models.JwtUserRepository;
import org.example.SecurityConfigurer.*;
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@RestController
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Autowired AuthenticationManager authenticationManager;
    @Autowired MyUserDetailsService userDetailsService;
    @Autowired
    JwtUserRepository jwtUserRepository;
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authenticate2")
    public ResponseEntity<?> authenticate(@RequestBody @NotNull AuthenticationRequest authenticationRequest) {
        System.out.println("Main.authenticate: " + authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody @NotNull SignUpRequest signUpRequest)
    {
        System.out.println("Main.registerUser: " + signUpRequest.getEmail() + " " + signUpRequest.getUsername() + " " + signUpRequest.getPassword());


        if(jwtUserRepository.findUserByEmail(signUpRequest.getEmail())!= null)
        {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseEntity<>("Error: Email is already taken!", null, 400));
        }
        JwtUser jwtUser = new JwtUser(signUpRequest.getUsername(),signUpRequest.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        jwtUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        jwtUserRepository.save(jwtUser);
        return ResponseEntity.ok(new ResponseEntity<>("User registered successfully", null, 200));

    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@org.jetbrains.annotations.NotNull @RequestBody LoginRequest loginRequest) {
        System.out.println("Main.authenticateUser: " + loginRequest.getEmail() + " " + loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);
            ResponseCookie springCookie = ResponseCookie.from("token",jwt)
                    .httpOnly(true)
                    .secure(true)
                    .path("/login")
                    .maxAge(600000)
                    .domain("localhost")
                    .build();
            ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .build();
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        catch (Exception e) {
            System.out.println("Main.authenticateUser: " + e.getMessage());
        }
        return ResponseEntity.ok(new ResponseEntity("Username or password is wrong", null, 400));

    }


}
