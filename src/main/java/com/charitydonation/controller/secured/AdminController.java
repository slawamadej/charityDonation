package com.charitydonation.controller.secured;

import com.charitydonation.service.InstitutionService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    private final InstitutionService institutionService;

    public AdminController(InstitutionService institutionService){
        this.institutionService = institutionService;
    }

    @GetMapping("/adminpanel")
    public String adminPanel(){
        return "/adminpanel";
    }

}
