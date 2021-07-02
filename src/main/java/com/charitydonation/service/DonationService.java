package com.charitydonation.service;

import com.charitydonation.entity.Donation;
import com.charitydonation.entity.User;
import com.charitydonation.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository){
        this.donationRepository = donationRepository;
    }

    public Donation merge(Donation donation){
        return donationRepository.save(donation);
    }

    public Long totalQuantity(){
        return donationRepository.totalQuantity();
    }

    public Long totalDonations() {
        return donationRepository.count();
    }

    public List<Donation> findAllByUserId(Long id){
        return donationRepository.findAllByUserId(id);
    }
}
