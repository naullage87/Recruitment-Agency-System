package com.abc.agency.job.service;

import com.abc.agency.job.entity.UserAccount;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {

    public ResponseEntity<Object> registerUser(UserAccount userAccount);
    public ResponseEntity<Object> autheticate();
}
