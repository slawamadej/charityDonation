package com.charitydonation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CharityDonationProjectApplication {

    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    public static void main(String[] args) {
        SpringApplication.run(CharityDonationProjectApplication.class, args);
    }

}
