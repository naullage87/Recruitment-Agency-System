package com.abc.agency.job.repository;

import com.abc.agency.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> ,UserRepositoryCustom{
    Optional<User> findByUserAccountId(Integer id);
}