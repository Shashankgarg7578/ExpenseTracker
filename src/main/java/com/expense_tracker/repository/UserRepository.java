package com.expense_tracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.entities.UserInfo;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {

	UserInfo findByUsername(String username);

}
