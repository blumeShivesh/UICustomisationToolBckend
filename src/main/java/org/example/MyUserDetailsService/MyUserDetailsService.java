package org.example.MyUserDetailsService;
import org.example.models.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.example.models.JwtUserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    JwtUserRepository jwtUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       JwtUser jwtUser=jwtUserRepository.findUserByEmail(email);
       if(jwtUser==null){
           throw new UsernameNotFoundException("User not found with email: " + email);
       }
         return new User(jwtUser.getEmail(),jwtUser.getPassword(),new ArrayList<>());
    }
}

