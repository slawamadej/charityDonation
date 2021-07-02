package com.charitydonation.service;

import com.charitydonation.entity.Institution;
import com.charitydonation.repository.InstitutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository){
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> findAll(){
        return institutionRepository.findAll();
    }
    public Institution findById(Long id){
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if(optionalInstitution.isPresent()){
            return optionalInstitution.get();
        }
        return null;
    }

    public void removeInstitution(Long id){
        Optional<Institution> optionalInstitution = institutionRepository.findById(id);
        if(optionalInstitution.isPresent()){
            Institution institutionToDelete = optionalInstitution.get();
            institutionRepository.delete(institutionToDelete);
        }
    }

    public Institution merge(Institution institution){
        return institutionRepository.save(institution);
    }
}
