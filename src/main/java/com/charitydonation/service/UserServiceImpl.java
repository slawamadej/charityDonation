package com.charitydonation.service;

import com.charitydonation.CustomMailSender;
import com.charitydonation.entity.Role;
import com.charitydonation.entity.User;
import com.charitydonation.repository.RoleRepository;
import com.charitydonation.repository.UserRepository;
import com.charitydonation.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomMailSender customMailSender;
    private final PasswordResetTokenService passwordResetTokenService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, CustomMailSender customMailSender
            , PasswordResetTokenService passwordResetTokenService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customMailSender = customMailSender;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(0);
        user.setToken(uuid);
        Role userRole = roleRepository.findByName("ROLE_USER");
        if(userRole == null){
            return null;
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        User savedUser = userRepository.save(user);
        customMailSender.sendConfirmationEmail(user.getUsername(), user.getToken());

        return savedUser;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User confirmUser(String token) {
        User userByToken = userRepository.findByToken(token);
        if(userByToken == null){
            return null;
        }
        userByToken.setEnabled(1);
        return userRepository.save(userByToken);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
