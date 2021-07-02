package com.charitydonation.controller;

import com.charitydonation.CurrentUser;
import com.charitydonation.entity.*;
import com.charitydonation.service.CategoryService;
import com.charitydonation.service.DonationService;
import com.charitydonation.service.InstitutionService;
import com.charitydonation.service.interfaces.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DonationController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final UserService userService;

    public DonationController(InstitutionService institutionService, DonationService donationService, CategoryService categoryService, UserService userService){
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/form")
    public String form(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        Donation donation = new Donation();
        model.addAttribute("donation", donation);

        List<Institution> institutions = institutionService.findAll();
        model.addAttribute("institutions", institutions);

        List<Category> categoriesList = categoryService.findAll();
        model.addAttribute("categoriesList", categoriesList);

        return "form";
    }

    @PostMapping("/form-confirmation")
    public String formConfirmation(@AuthenticationPrincipal CurrentUser currentUser, @Valid Donation donation, BindingResult result, Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            model.addAttribute("donation",donation);
            List<Institution> institutions = institutionService.findAll();
            model.addAttribute("institutions", institutions);

            List<Category> categoriesList = categoryService.findAll();
            model.addAttribute("categoriesList", categoriesList);
            return "redirect:/form";
        }


        if(currentUser == null){
            System.out.println("BRAK CURRENT USER");
        }
        User user = userService.findByUsername("smadej@gmail.com");
        donation.setUser(user);
        Donation mergedDonation = donationService.merge(donation);
        return "form-confirmation";
    }

    @GetMapping("/myDonations")
    public String myDonations(@AuthenticationPrincipal CurrentUser currentUser, Model model){
        User user = userService.findByUsername("smadej@gmail.com");
        //donation.setUser(currentUser.getUser());

        //List<Donation> donations = donationService.findAllByUserId(currentUser.getId());
        List<Donation> donations = donationService.findAllByUserId(user.getId());

        model.addAttribute("donations", donations);

        return "donations";
    }
}
