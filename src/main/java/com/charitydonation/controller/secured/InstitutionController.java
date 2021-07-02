package com.charitydonation.controller.secured;

import com.charitydonation.entity.Institution;
import com.charitydonation.service.InstitutionService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
public class InstitutionController {


    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService){
        this.institutionService = institutionService;
    }

    @GetMapping("/institutions")
    public String institutions(Model model){
        List<Institution> institutions = institutionService.findAll();
        model.addAttribute("institutions", institutions);
        return "institutions";
    }

    @PostMapping("/institutions/{id}/remove")
    public String removeInstitution(Model model, @PathVariable("id") Long id){
        Institution institution = institutionService.findById(id);
        if(institution != null){
            institutionService.removeInstitution(id);
            model.addAttribute("notification", "Removed institution with id:" + id);
            List<Institution> institutions = institutionService.findAll();
            model.addAttribute("institutions", institutions);
        } else {
            model.addAttribute("notification", "There is no institution with id:" + id);
        }
        return "redirect:/institutions";
    }

    @GetMapping("/institutions/{id}")
    public String showInstitution(Model model, @PathVariable("id") Long id){
        Institution institution = institutionService.findById(id);
        if(institution != null){
            model.addAttribute("institution", institution);
        } else {
            model.addAttribute("notification", "There is no institution with id:" + id);
        }

        return "institution";
    }

    @GetMapping("/institutions/add")
    public String addInstitutionForm(Model model){
        Institution institution = new Institution();
        model.addAttribute("institution", institution);
        return "institution";
    }

    @PostMapping("/institutions/{id}")
    public String editInstitutions(@Valid Institution institution, BindingResult result, Model model, @PathVariable Long id){
        //TODO jesli result error
        if(id > 0){
            Institution institutionById = institutionService.findById(id);
            if(institutionById != null){
                institution.setId(id);
            }
        }
        Institution savedInstitution = institutionService.merge(institution);

        return "redirect:/institutions";
    }


}
