package com.charitydonation;

import com.charitydonation.entity.User;
import com.charitydonation.service.UserServiceImpl;
import com.charitydonation.service.spring.SpringDataUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SpringDataUserDetailsService springDataUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(SpringDataUserDetailsService springDataUserDetailsService, PasswordEncoder passwordEncoder){
        this.springDataUserDetailsService = springDataUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();

        System.out.println("CUSTOM AUTH PROVIDER");

        if(username == null || credentials == null){
            throw new BadCredentialsException("User and password cant be null");
        }

        if(!(credentials instanceof String)){
            return null;
        }

        String password = credentials.toString();

        UserDetails userDetails = springDataUserDetailsService.loadUserByUsername(username);

        //docelowo password encoder
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
