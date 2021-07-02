package com.charitydonation.controller;

import com.charitydonation.entity.Contact;
import com.charitydonation.entity.PasswordResetToken;
import com.charitydonation.entity.User;
import com.charitydonation.service.PasswordResetTokenService;
import com.charitydonation.service.UserServiceImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final PasswordResetTokenService passwordResetTokenService;

    public UserController(UserServiceImpl userServiceImpl, PasswordResetTokenService passwordResetTokenService){
        this.userServiceImpl = userServiceImpl;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping("/login")
    public String login(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid User user, Model model){
        //sprawdzenie czy dobre dane
        //TODO
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(Model model, User user){
        User savedUser = userServiceImpl.saveUser(user);
        if(savedUser == null){
            model.addAttribute("notification", "It was not possible to create user.");
            return "/register";
        }
        return "redirect:/";
    }

    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam("token") String token, Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        User confirmedUser = userServiceImpl.confirmUser(token);
        if(confirmedUser == null){
            model.addAttribute("notification", "It was not possible to confirm user.");
            return "redirect:register";
        }
        return "redirect:login";
    }

    @GetMapping("/forgetPassword")
    public String changePassword(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        User user = new User();
        model.addAttribute("user", user);

        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String changePasswordEmail(Model model, User user, BindingResult result){
        String email = user.getUsername();
        if(email != null) {
            PasswordResetToken passwordResetToken = passwordResetTokenService.forgetPassword(email);
            if (passwordResetToken != null) {
                return "redirect:/login";
            }
        }
        return "redirect:/forgetPassword";
    }

    @GetMapping("/changePassword")
    public String changePasswordToken(@RequestParam("token") String token, Model model){
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        if(passwordResetToken != null){
            User userToResetPassword = passwordResetToken.getUser();
            model.addAttribute("user", userToResetPassword);
            return "resetPassword";
        }

        return "redirect:forgetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(User user){
        User userChangePassword = userServiceImpl.findByUsername(user.getUsername());
        userServiceImpl.changeUserPassword(userChangePassword, user.getPassword());

        return "redirect:/login";
    }

    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    public String users(Model model){
        List<User> users = userServiceImpl.findAll();
        model.addAttribute("users", users);
        return "users";
    }

}
