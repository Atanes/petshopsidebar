package com.iridiumit.petshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iridiumit.petshop.security.PetShopUserDetailsService;


@Configuration
public class UdsSecurityConfig {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder, 
			PasswordEncoder passwordEncoder, 
			PetShopUserDetailsService userDetailsService) throws Exception {
		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
}
