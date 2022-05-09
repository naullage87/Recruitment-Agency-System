package com.abc.agency.job.repository;

import com.abc.agency.job.entity.ProfessionalExperiance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ProfessionalExperianceRepository extends JpaRepository<ProfessionalExperiance, Integer> {


    List<ProfessionalExperiance> findByUserId(Integer id);

    @Modifying
    void deleteByUserId(Integer id);
}