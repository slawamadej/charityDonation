package com.charitydonation;

import com.charitydonation.service.spring.SpringDataUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(AuthSuccessHandler authSuccessHandler){
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/forgetPassword").permitAll()
                .antMatchers("/static/css/**").permitAll()
                .antMatchers("/static/images/**").permitAll()
                .antMatchers("/static/js/**").permitAll()
                .antMatchers("/form").authenticated()
                .antMatchers("/form-confirmation").authenticated()
                .antMatchers("/myDonations").authenticated()
                .antMatchers("/myUser").authenticated()
                .antMatchers("/adminpanel").hasRole("ADMIN")
                .antMatchers("/institutions").hasRole("ADMIN")
                .antMatchers("/institutions/**").hasRole("ADMIN")
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/contacts").hasRole("ADMIN")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(this.authSuccessHandler)
                    //.defaultSuccessUrl("/", false)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                    .and()
                        .csrf().disable();
    }
}
