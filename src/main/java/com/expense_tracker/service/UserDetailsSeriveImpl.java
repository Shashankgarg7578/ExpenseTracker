package com.expense_tracker.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.expense_tracker.entities.UserInfo;
import com.expense_tracker.model.UserInfoDto;
import com.expense_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@AllArgsConstructor
@Data
public class UserDetailsSeriveImpl implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = userRepository.findByUsername(username);

		if (userInfo == null) {
			throw new UsernameNotFoundException("Could not found user..!!");
		}

		return new CustomUserDetails(userInfo);
	}

	public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto) {
		return userRepository.findByUsername(userInfoDto.getUsername());
	}
	
	public Boolean signupUser(UserInfoDto userInfoDto) {
      //TODO: Define a function in ValidityClass to check if UserEmail and Password.

		userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

		if (Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))) {
			return false;
		}

		String userId = UUID.randomUUID().toString();
		userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet()));

		return true;
	}

}
