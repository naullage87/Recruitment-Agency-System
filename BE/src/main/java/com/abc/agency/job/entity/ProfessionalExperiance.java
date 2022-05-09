package com.abc.agency.job.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ProfessionalExperiance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer jobSectorId;
    private String organization;
    private String designation;
    private Date fromDate;
    private Date toDate;
    private String description;
    private Integer years;
    private Integer userId;

}
