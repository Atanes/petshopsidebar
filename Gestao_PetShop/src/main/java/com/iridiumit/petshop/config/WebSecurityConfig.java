package com.iridiumit.petshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.iridiumit.petshop.security.PetShopUserDetailsService;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PetShopUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				.antMatchers("/").hasAnyRole("PS_ADMIN", "PS_ATENDIMENTO", "PS_VETERINARIO", "PS_CAIXA")
				.antMatchers("/administracao/**").hasAnyRole("PS_ADMIN")
				.antMatchers("/relatorios/**").hasAnyRole("PS_ADMIN","PS_VETERINARIO")
				.antMatchers("/atendimento/**").hasAnyRole("PS_ADMIN","PS_ATENDIMENTO", "PS_VETERINARIO")
				.antMatchers("/veterinario/**").hasAnyRole("PS_ADMIN","PS_VETERINARIO")
				.antMatchers("/caixa/**").hasAnyRole("PS_ADMIN","PS_CAIXA")
				.anyRequest()
				.authenticated()
				.antMatchers("/resources/**", "/signup", "/about", "/webjars/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/entrar")
				.permitAll()
			.and()
			.logout()
				.logoutSuccessUrl("/entrar?logout")
				.permitAll()
			.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
			.and()
			.exceptionHandling().accessDeniedPage("/acessonegado");;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**");
	}
}
