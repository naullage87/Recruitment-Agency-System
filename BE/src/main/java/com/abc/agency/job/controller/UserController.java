package com.abc.agency.job.controller;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.User;
import com.abc.agency.job.entity.UserAccount;
import com.abc.agency.job.service.UserAccountService;
import com.abc.agency.job.service.UserService;
import com.abc.agency.job.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(Constants.API_GET_USER_DETAILS)
    public ResponseEntity<Object> getUserDetails() {
        return userService.getUserDetails();
    }

    @PostMapping(Constants.API_UPDATE_USER)
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping(Constants.API_DOWNLOAD_CV)
    public ResponseEntity<Object> downloadCV() {
        return userService.downloadCV();
    }

    @GetMapping(Constants.API_DOWNLOAD_USER_CV)
    public ResponseEntity<Object> downloadCV(@RequestParam Integer userId) {
        return userService.downloadCV(userId);
    }

    @PostMapping(Constants.API_SEARCH_CVS)
    public ResponseEntity<Object> searchCVs(@RequestBody SearchFilter searchFilter) {
        return userService.searchCVs(searchFilter);
    }
}
