package com.abc.agency.job.service.impl;

import com.abc.agency.job.entity.User;
import com.abc.agency.job.entity.UserAccount;
import com.abc.agency.job.repository.UserAccountRepository;
import com.abc.agency.job.repository.UserRepository;
import com.abc.agency.job.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method use to register new jobseeker account
     * @param userAccount
     * @return
     */
    @Override
    public ResponseEntity<Object> registerUser(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccountRepository.save(userAccount);
        User user = new User(userAccount.getFirstName(), userAccount.getLastName(), userAccount.getEmail());
        user.setUserAccountId(userAccount.getId());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * This method use to authenticate a user
     * @return
     */
    @Override
    public ResponseEntity<Object> autheticate() {

        UserAccount userAcc = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userAcc.getType(), HttpStatus.OK);
    }
}
