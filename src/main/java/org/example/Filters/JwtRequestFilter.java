package org.example.jwt;

import org.example.util.jwtUtil;
import org.example.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.exception.CustomException;

@Component
public class JwtRequestFilter implements Filter {

    @Autowired
    jwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService userDetailsService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException,CustomException{
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        String rawCookie = httpServletRequest.getHeader("Cookie");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authorizationHeader = httpServletRequest.getHeader("authorization");
        System.out.println("authorizationHeader: "+authorizationHeader);
        String jwtToken = null;
        String username = null;
        String bearerToken = httpServletRequest.getHeader("Authorization");
        System.out.println("bearerToken: "+bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            try {
                jwtToken = bearerToken.substring(7, bearerToken.length());
                username = jwtUtil.extractUsername(jwtToken);
            }
            catch (Exception e){
                System.out.println("Exception: "+e);
            }
            System.out.println("jwtToken: "+jwtToken);
            System.out.println("username: "+username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            else{
                System.out.println("jwtToken is null");
//                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        }
        chain.doFilter(request, response);
        }
}