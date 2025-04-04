package com.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByToken(String token);

}