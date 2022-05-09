package com.abc.agency.job.repository;

import com.abc.agency.job.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AwardRepository extends JpaRepository<Award, Integer> {

    @Query("SELECT name FROM Award WHERE id=:id")
    String getNameById(@Param("id")Integer id);

}