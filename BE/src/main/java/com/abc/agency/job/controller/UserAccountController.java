package com.abc.agency.job.controller;

import com.abc.agency.job.entity.UserAccount;
import com.abc.agency.job.service.UserAccountService;
import com.abc.agency.job.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping(Constants.API_REGISTER_USER)
    public ResponseEntity<Object> registerUser(@RequestBody UserAccount userAccount) {
        return userAccountService.registerUser(userAccount);
    }

    @GetMapping(Constants.API_AUTHENTICATE)
    public ResponseEntity<Object> autheticate() {
        return userAccountService.autheticate();
    }
}
