package com.abc.agency.job;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.SchoolEducation;
import com.abc.agency.job.entity.User;
import com.abc.agency.job.entity.UserAccount;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class JobApplicationTests {

    private TestRestTemplate restTemplate;

    private String loggedUserName = "kathie";
    private String loggedPasseword = "123456789";

    @Before
    public void init() {
        restTemplate = new TestRestTemplate();
    }


    /**
     * This test case tests job seeker registration
     *
     * @throws URISyntaxException
     */
    @Test
    public void registerJobSeeker() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/registerUser";
        URI uri = new URI(baseUrl);
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName("Kathie");
        userAccount.setLastName("Perera");
        userAccount.setEmail("kathie@gmail.com");
        userAccount.setPassword(loggedPasseword);
        userAccount.setUsername(loggedUserName);
        userAccount.setType(2);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<UserAccount> request = new HttpEntity<>(userAccount, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(201, result.getStatusCodeValue());
    }

    /**
     * This case test authentication of newly registered user
     *
     * @throws URISyntaxException
     */
    @Test
    public void authenticate() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/authenticate";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createAuthHeader(loggedUserName,loggedPasseword));

        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        //Verify bad request and missing header
        Assert.assertEquals(200, result.getStatusCodeValue());
    }


    /**
     * This case use to test updation of user details of logged user
     *
     * @throws URISyntaxException
     */
    @Test
    public void updateUserDetails() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/updateUser";

        URI uri = new URI(baseUrl);

        User user = new User();
        user.setFirstName("update test");
        user.setLastName("update test");
        user.setEmail("unitTest@gmail.com");
        user.setAddress1("test");
        user.setAddress2("test road");
        user.setAddress3("test");
        user.setDateOfBirth(new Date());
        user.setGender('M');
        user.setMaried('Y');
        user.setMobile("00000000");
        user.setTelephone("11111111");
        user.setNationality("Sri Lankan");
        user.setNic("777777777V");

        SchoolEducation schoolEducation = new SchoolEducation();
        schoolEducation.setDateOfAchived(new Date());
        schoolEducation.setQualification("GCE-OL");
        schoolEducation.setNumberOfPasses(3);
        schoolEducation.setScheme("General");

        List<SchoolEducation> schoolEducationList = new ArrayList<>();
        schoolEducationList.add(schoolEducation);
        user.setSchoolEducations(schoolEducationList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createAuthHeader(loggedUserName,loggedPasseword));
        headers.set("Content-Type", "application/json");

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }


    /**
     * This case use to download generated CV PDF document of logged user.
     *
     * @throws URISyntaxException
     */
    @Test
    public void downloadUserCV() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/downloadCV";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createAuthHeader(loggedUserName,loggedPasseword));

        ResponseEntity<String> result = this.restTemplate.exchange(baseUrl, HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        //Verify bad request and missing header
        Assert.assertNotEquals(0, result.getBody().getBytes().length);
    }

    /**
     * This case use to download generated CV PDF document of logged user.
     *
     * @throws URISyntaxException
     */
    @Test
    public void searchCVs() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/searchCVs";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createAuthHeader("admin","123"));

        SearchFilter searchFilter = new SearchFilter();
////        searchFilter.setAlMinimumPasses(2);
////        searchFilter.setProfessionalExperianceYears(3);
//        searchFilter.setJobSectorId(2);
////        searchFilter.setAwardId(1);

        ResponseEntity<User[]> result = this.restTemplate.exchange(baseUrl, HttpMethod.POST,
                new HttpEntity<>(searchFilter,headers), User[].class);

        System.out.println("<< COUNT "+result.getBody().length+">>");

        Assert.assertNotEquals(0, result.getBody().length);
    }


    /**
     * This helper method generate auth code
     *
     * @return
     */
    private String createAuthHeader(String loggedUserName,String loggedPasseword) {
        String authString = loggedUserName + ":" + loggedPasseword;
        byte[] encodedAuth = Base64.encodeBase64(
                authString.getBytes(Charset.forName("US-ASCII")));
        String authCode = "Basic " + new String(encodedAuth);
        System.out.println(authCode);
        return authCode;
    }

}
