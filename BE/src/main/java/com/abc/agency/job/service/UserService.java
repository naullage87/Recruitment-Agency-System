package com.abc.agency.job.service;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<Object> getUserDetails();
    public ResponseEntity<Object> updateUser( User user);
    public ResponseEntity<Object> downloadCV();
    public ResponseEntity<Object> downloadCV(Integer userId);
    public ResponseEntity<Object> searchCVs(SearchFilter searchFilter);
}
