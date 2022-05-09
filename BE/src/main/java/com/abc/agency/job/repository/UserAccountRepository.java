package com.abc.agency.job.repository;

import com.abc.agency.job.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {


    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByUsernameAndIdNot(String username,Integer id);
}