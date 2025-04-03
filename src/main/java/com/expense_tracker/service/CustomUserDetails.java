package com.expense_tracker.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.expense_tracker.entities.UserInfo;
import com.expense_tracker.entities.UserRole;

public class CustomUserDetails extends UserInfo implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String username;

	String password;

	Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(UserInfo userInfo) {
		this.username = userInfo.getUsername();
		this.password = userInfo.getPassword();
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();

		for (UserRole role : userInfo.getRoles()) {
			auth.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
