package com.charitydonation.controller;

import com.charitydonation.entity.Contact;
import com.charitydonation.entity.Institution;
import com.charitydonation.service.DonationService;
import com.charitydonation.service.InstitutionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService){
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @RequestMapping("/")
    public String home(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        List<Institution> institutions = institutionService.findAll();
        model.addAttribute("institutions", institutions);

        Long totalQuantity = donationService.totalQuantity();
        model.addAttribute("totalQuantity", totalQuantity);

        Long totalDonations = donationService.totalDonations();
        model.addAttribute("totalDonations", totalDonations);

        return "index";
    }
}
