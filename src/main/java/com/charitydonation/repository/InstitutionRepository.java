package com.charitydonation.repository;

import com.charitydonation.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
