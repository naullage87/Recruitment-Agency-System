package com.abc.agency.job.repository;

import com.abc.agency.job.entity.HigherEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface HigherEducationRepository extends JpaRepository<HigherEducation, Integer> {


    List<HigherEducation> findByUserId(Integer id);

    @Modifying
    void deleteByUserId(Integer id);
}