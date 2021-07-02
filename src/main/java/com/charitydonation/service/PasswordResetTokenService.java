package com.charitydonation.service;

import com.charitydonation.CustomMailSender;
import com.charitydonation.entity.PasswordResetToken;
import com.charitydonation.entity.User;
import com.charitydonation.repository.PasswordResetTokenRepository;
import com.charitydonation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final CustomMailSender customMailSender;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository
            , UserRepository userRepository, CustomMailSender customMailSender){
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.customMailSender = customMailSender;
    }

    public PasswordResetToken merge(PasswordResetToken passwordResetToken){
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken findByToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken forgetPassword(String username){
        User user = userRepository.findByUsername(username);
        if(user != null){
            System.out.println("ZNALEZIONO USERA");
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDateTime(LocalDateTime.now());

            PasswordResetToken passwordResetTokenSaved = this.merge(passwordResetToken);
            if(passwordResetTokenSaved != null) {
                System.out.println("NIE NULLOWY password");
                customMailSender.sendChangePasswordLink(passwordResetTokenSaved);
                return passwordResetToken;
            }
            System.out.println("NULOWY TOKEN RESET");
        }
        return null;
    }
}
