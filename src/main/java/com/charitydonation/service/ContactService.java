package com.charitydonation.service;

import com.charitydonation.CustomMailSender;
import com.charitydonation.entity.Contact;
import com.charitydonation.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CustomMailSender customMailSender;

    public ContactService(ContactRepository contactRepository, CustomMailSender customMailSender){
        this.contactRepository = contactRepository;
        this.customMailSender = customMailSender;
    }

    public Contact merge(Contact contact){
        Contact savedContact = contactRepository.save(contact);
        customMailSender.sendContactEmail(savedContact);
        return savedContact;
    }

    public List<Contact> findAllContact(){
        return contactRepository.findAll();
    }
}
