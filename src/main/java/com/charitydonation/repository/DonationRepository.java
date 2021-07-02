package com.charitydonation.repository;

import com.charitydonation.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "SELECT COALESCE(SUM(d.quantity), 0) FROM Donation d")
    Long totalQuantity();

    @Query(value = "SELECT d FROM Donation d WHERE d.user.id = :id")
    List<Donation> findAllByUserId(@Param("id") Long id);

}
