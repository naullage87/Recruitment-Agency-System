package com.abc.agency.job.repository;

import com.abc.agency.job.entity.SchoolEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface SchoolEducationRepository extends JpaRepository<SchoolEducation, Integer> {


    List<SchoolEducation> findByUserId(Integer id);

    @Modifying
    void deleteByUserId(Integer id);
}