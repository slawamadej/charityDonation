package com.charitydonation.service.spring;

import com.charitydonation.CurrentUser;
import com.charitydonation.entity.User;
import com.charitydonation.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserRepository(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findByUsername(username);
        if (user == null || user.getEnabled() != 1) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r -> grantedAuthorities.add(
                        new SimpleGrantedAuthority(r.getName())));
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
