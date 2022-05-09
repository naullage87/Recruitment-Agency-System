package com.abc.agency.job.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchFilter {
    private Integer jobSectorId;
    private List<String> schoolEducationQualifications;
    private Integer olMinimumPasses;
    private Integer alMinimumPasses;
    private Integer awardId;
    private Integer professionalExperianceJobSectorId;
    private Integer professionalExperianceYears;
    private String skill;


}
