package com.charitydonation.service.interfaces;

import com.charitydonation.entity.PasswordResetToken;
import com.charitydonation.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String name);
    User saveUser(User user);
    List<User> findAll();

    User confirmUser(String token);

    void changeUserPassword(User user, String password);
}
