package com.abc.agency.job.repository;

import com.abc.agency.job.entity.SkillsAndAchievment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface SkillsAndAchievementRepository extends JpaRepository<SkillsAndAchievment, Integer> {


    List<SkillsAndAchievment> findByUserId(Integer id);

    @Modifying
    void deleteByUserId(Integer id);

}