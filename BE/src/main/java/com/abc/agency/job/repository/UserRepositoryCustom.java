package com.abc.agency.job.repository;

import com.abc.agency.job.dto.SearchFilter;
import com.abc.agency.job.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

    public List<User> searchUsers(SearchFilter filters);
}
