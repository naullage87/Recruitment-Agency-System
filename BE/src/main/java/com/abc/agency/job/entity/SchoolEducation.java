package com.abc.agency.job.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class SchoolEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String qualification;
    private String scheme;
    private Date dateOfAchived;
    private Integer numberOfPasses;

    private Integer userId;


}
