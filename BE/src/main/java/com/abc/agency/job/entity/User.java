package com.abc.agency.job.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Character gender;
    private Character maried;
    private String nationality;
    private String nic;
    private String address1;
    private String address2;
    private String address3;
    private String telephone;
    private String mobile;
    private String email;
    private Integer jobSectorId;
    private Integer userAccountId;

    private transient List<SchoolEducation> schoolEducations;
    private transient List<HigherEducation> higherEducations;
    private transient List<ProfessionalExperiance> professionalExperiances;
    private transient List<SkillsAndAchievment> skillsAndAchievments;


    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
