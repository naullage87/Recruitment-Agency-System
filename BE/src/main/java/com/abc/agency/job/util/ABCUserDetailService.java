/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc.agency.job.util;

import com.abc.agency.job.entity.UserAccount;
import com.abc.agency.job.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author kashy
 */
@Service
public class ABCUserDetailService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Optional<UserAccount> userOpt = userRepository.findByUsername(string);
        if(userOpt.isPresent()){
            return userOpt.get();
        }
        else{
            throw new UsernameNotFoundException("Not found : " + string);
        }
    }
}
