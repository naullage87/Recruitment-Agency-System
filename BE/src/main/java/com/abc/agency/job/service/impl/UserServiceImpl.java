package com.abc.agency.job.service.impl;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.*;
import com.abc.agency.job.repository.*;
import com.abc.agency.job.service.UserService;
import com.abc.agency.job.util.ABCException;
import com.abc.agency.job.util.Constants;
import com.abc.agency.job.util.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Calendar.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolEducationRepository schoolEducationRepository;
    @Autowired
    private HigherEducationRepository higherEducationRepository;
    @Autowired
    private ProfessionalExperianceRepository professionalExperianceRepository;
    @Autowired
    private SkillsAndAchievementRepository skillsAndAchievementRepository;
    @Autowired
    private ReportGenerator reportGenerator;
    @Autowired
    private AwardRepository awardRepository;

    /**
     * This method use to fetch user details of logged in user.
     *
     * @return
     */
    @Override
    public ResponseEntity<Object> getUserDetails() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByUserAccountId(userAccount.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSchoolEducations(schoolEducationRepository.findByUserId(user.getId()));
            user.setHigherEducations(higherEducationRepository.findByUserId(user.getId()));
            user.setProfessionalExperiances(professionalExperianceRepository.findByUserId(user.getId()));
            user.setSkillsAndAchievments(skillsAndAchievementRepository.findByUserId(user.getId()));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method use to update existing data of logged user
     *
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<Object> updateUser(User user) {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByUserAccountId(userAccount.getId());
        User existingUser = null;
        if (userOptional.isPresent()) {
            existingUser = userOptional.get();
        }
        if (null == existingUser) {
            throw new ABCException(Constants.ERROR_MESSAGE_USER_NOT_FOUND);
        }
        user.setId(existingUser.getId());
        user.setUserAccountId(existingUser.getUserAccountId());
        userRepository.save(user);

        //Delete existing child records
        schoolEducationRepository.deleteByUserId(user.getId());
        higherEducationRepository.deleteByUserId(user.getId());
        professionalExperianceRepository.deleteByUserId(user.getId());
        skillsAndAchievementRepository.deleteByUserId(user.getId());

        saveSchoolEducationDetails(user);
        saveHigherEducationDetails(user);
        saveExperianceDetails(user);
        saveSkilsAndAcheivementDetails(user);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method use to create skills and acheivement details of the given user
     *
     * @param user
     */
    private void saveSkilsAndAcheivementDetails(User user) {
        if (null == user.getSkillsAndAchievments()) {
            return;
        }
        for (SkillsAndAchievment skillsAndAchievment : user.getSkillsAndAchievments()) {
            skillsAndAchievment.setUserId(user.getId());
            skillsAndAchievementRepository.save(skillsAndAchievment);
        }

    }

    /**
     * This method use to create experiance details of the given user
     *
     * @param user
     */
    private void saveExperianceDetails(User user) {
        if (null == user.getProfessionalExperiances()) {
            return;
        }
        for (ProfessionalExperiance professionalExperiance : user.getProfessionalExperiances()) {
            professionalExperiance.setUserId(user.getId());
            professionalExperiance.setYears(getDiffYears(professionalExperiance.getFromDate(), professionalExperiance.getToDate()));
            professionalExperianceRepository.save(professionalExperiance);
        }
    }

    /**
     * This method use to create higher eduction details of the given user
     *
     * @param user
     */
    private void saveHigherEducationDetails(User user) {
        if (null == user.getHigherEducations()) {
            return;
        }
        for (HigherEducation higherEducation : user.getHigherEducations()) {
            higherEducation.setUserId(user.getId());
            higherEducationRepository.save(higherEducation);
        }

    }

    /**
     * This method use to create school eduction details of the given user
     *
     * @param user
     */
    private void saveSchoolEducationDetails(User user) {
        if (null == user.getSchoolEducations()) {
            return;
        }
        for (SchoolEducation schoolEducation : user.getSchoolEducations()) {
            schoolEducation.setUserId(user.getId());
            schoolEducationRepository.save(schoolEducation);
        }

    }

    /**
     * This method use to find difference of time in years of given two dates
     *
     * @param first
     * @param last
     * @return
     */
    private int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    /**
     * This helper method creates and returns calander object from the given date object
     *
     * @param date
     * @return
     */
    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    /**
     * Use to download a generated CV for logged user.
     *
     * @return
     */
    @Override
    public ResponseEntity<Object> downloadCV() {

        ResponseEntity<Object> userResponse = getUserDetails();
        List<User> userList = new ArrayList<>();
        User user = (User) userResponse.getBody();
        if (null != user && null != user.getHigherEducations()) {
            for (HigherEducation higEdu : user.getHigherEducations()) {
                higEdu.setAward(awardRepository.getNameById(higEdu.getAwardId()));
            }
        }
        userList.add(user);

        byte[] reportData = reportGenerator.generatePDFReport(userList, new HashMap<>(), Constants.REPORT_TEMPLATE_CV);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Constants.APPLICATION_TYPE_PDF))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        Constants.ATTACHMENT_NAME_ATTR + Constants.FILE_NAME_CV + Constants.DOUBLE_QUOTE)
                .body(new ByteArrayResource(reportData));
    }

    /**
     * Provides a generated CV for the given user id
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<Object> downloadCV(Integer userId) {
        Optional<User> userOptional = userRepository.findByUserAccountId(userId);
        if (!userOptional.isPresent()) {
            throw new ABCException(Constants.ERROR_MESSAGE_USER_NOT_FOUND);
        }
        User user = userOptional.get();
        user.setSchoolEducations(schoolEducationRepository.findByUserId(user.getId()));
        user.setHigherEducations(higherEducationRepository.findByUserId(user.getId()));
        user.setProfessionalExperiances(professionalExperianceRepository.findByUserId(user.getId()));
        user.setSkillsAndAchievments(skillsAndAchievementRepository.findByUserId(user.getId()));

        if (null != user.getHigherEducations()) {
            for (HigherEducation higEdu : user.getHigherEducations()) {
                higEdu.setAward(awardRepository.getNameById(higEdu.getAwardId()));
            }
        }
        List<User> userList = new ArrayList<>();
        userList.add(user);
        byte[] reportData = reportGenerator.generatePDFReport(userList, new HashMap<>(), Constants.REPORT_TEMPLATE_CV);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Constants.APPLICATION_TYPE_PDF))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        Constants.ATTACHMENT_NAME_ATTR + Constants.FILE_NAME_CV + Constants.DOUBLE_QUOTE)
                .body(new ByteArrayResource(reportData));
    }

    /**
     * This method use to filter cvs according to given search criteria
     *
     * @param searchFilter
     * @return
     */
    @Override
    public ResponseEntity<Object> searchCVs(SearchFilter searchFilter) {
        return new ResponseEntity<>(userRepository.searchUsers(searchFilter), HttpStatus.OK);
    }
}
