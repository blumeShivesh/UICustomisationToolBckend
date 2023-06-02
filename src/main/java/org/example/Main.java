package org.example;

import org.example.MyUserDetailsService.MyUserDetailsService;
import org.example.models.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.example.util.jwtUtil;
import org.example.models.AuthenticationResponse;

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
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

}
