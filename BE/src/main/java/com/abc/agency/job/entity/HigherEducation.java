package com.abc.agency.job.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class HigherEducation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer awardId;
    private String institiuteOfStudy;
    private String affiliatedInstitute;
    private String areaOfStudy;
    private String description;
    private Integer result;
    private Integer userId;
    private transient String award;
}
