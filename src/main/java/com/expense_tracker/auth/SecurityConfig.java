package com.expense_tracker.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.expense_tracker.repository.UserRepository;
import com.expense_tracker.service.UserDetailsServiceImpl;

import lombok.Data;

//we will add here on which end-points we need authentication
@Configuration
@EnableMethodSecurity
@Data
public class SecurityConfig {

	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	@Autowired
	public UserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return new UserDetailsServiceImpl(userRepository, passwordEncoder);
	} 
	
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

	        return http
	                .csrf(AbstractHttpConfigurer::disable).cors(CorsConfigurer::disable) // disable all types of attack
	                .authorizeHttpRequests(auth -> auth
	                        .requestMatchers("/auth/v1/login", "/auth/v1/refreshToken", "/auth/v1/signup").permitAll()  // permit all these url's always
	                        .anyRequest().authenticated() // ask username , password always
	                )
	                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // dont save in local
	                .httpBasic(Customizer.withDefaults()) 
	                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //for filter requests
	                .authenticationProvider(authenticationProvider()) //for dont ask again-2 username password
	                .build();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
	        authenticationProvider.setPasswordEncoder(passwordEncoder);
	        return authenticationProvider;

	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	
}
