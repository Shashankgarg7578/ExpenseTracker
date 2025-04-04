package com.expense_tracker.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.expense_tracker.ExpenseTrackerApplication;
import com.expense_tracker.entities.RefreshToken;
import com.expense_tracker.entities.UserInfo;
import com.expense_tracker.repository.RefreshTokenRepository;
import com.expense_tracker.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	UserRepository userRepository;

	private final JwtService jwtService;

	private final ExpenseTrackerApplication expenseTrackerApplication;

	RefreshTokenService(ExpenseTrackerApplication expenseTrackerApplication, JwtService jwtService) {
		this.expenseTrackerApplication = expenseTrackerApplication;
		this.jwtService = jwtService;
	}

	public RefreshToken createRefreshToken(String username) {
		UserInfo userInfoExtracted = userRepository.findByUsername(username);
		RefreshToken refreshToken = RefreshToken.builder().userInfo(userInfoExtracted)
				.token(UUID.randomUUID().toString()).expiryDate(Instant.now().plusMillis(60000)).build();

		return refreshTokenRepository.save(refreshToken);
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login...");
		}

		return token;
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}
	
}