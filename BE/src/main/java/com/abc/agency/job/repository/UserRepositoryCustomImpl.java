package com.abc.agency.job.repository;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.SchoolEducation;
import com.abc.agency.job.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext()
    public EntityManager entityManager;

    @Override
    public List<User> searchUsers(SearchFilter filters) {

        String sql = "SELECT DISTINCT (user) FROM User user LEFT JOIN SchoolEducation school ON school.userId=user.id LEFT JOIN HigherEducation higher ON higher.userId=user.id" +
                " LEFT JOIN ProfessionalExperiance exp ON exp.userId=user.id LEFT JOIN SkillsAndAchievment skill ON skill.userId=user.id WHERE user.id!=null ";
        if (filters.getJobSectorId() != null) {
            sql += " AND user.jobSectorId=" + filters.getJobSectorId() + " ";
        }
        if (null != filters.getSchoolEducationQualifications()) {
            String value = "";
            for (String schoolEducation : filters.getSchoolEducationQualifications()) {
                value += schoolEducation + ",";
            }
            value = value.substring(0, value.length() - 1);
            sql += " AND school.qualification IN =(" + value + ")";
        }
        if (null != filters.getOlMinimumPasses()) {
            sql += " AND school.numberOfPasses >= " + filters.getOlMinimumPasses();
        }
        if (null != filters.getAlMinimumPasses()) {
            sql += " AND school.numberOfPasses >= " + filters.getAlMinimumPasses();
        }
        if (null != filters.getProfessionalExperianceJobSectorId()) {
            sql += " AND exp.jobSectorId = " + filters.getProfessionalExperianceJobSectorId();
        }
        if (null != filters.getProfessionalExperianceYears()) {
            sql += " AND exp.years >= " + filters.getProfessionalExperianceYears();
        }
        if(null!=filters.getAwardId()){
            sql+=" AND higher.awardId = "+filters.getAwardId();
        }
        if(null!=filters.getSkill()&& !filters.getSkill().isEmpty()){
            sql+= " AND skill.description like %"+filters.getSkill()+"%";
        }

        Query query = entityManager.createQuery(sql);
        List<User> data = query.getResultList();

        return data;
    }
}
