package com.charitydonation.controller;

import com.charitydonation.entity.Contact;
import com.charitydonation.service.ContactService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public String contactMessage(@Valid Contact contact, BindingResult result, Model model){
        //if(result.hasErrors()){
        //    return "form";
        //}
        Contact savedContact = contactService.merge(contact);
        contactService.merge(savedContact);
        return "redirect:/";
    }

    @GetMapping("/contacts")
    @Secured("ROLE_ADMIN")
    public String contacts(Model model){
        List<Contact> contacts = contactService.findAllContact();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }
}
