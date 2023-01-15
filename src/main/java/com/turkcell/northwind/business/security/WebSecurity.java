package com.turkcell.northwind.business.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.turkcell.northwind.business.abstracts.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		
		// problem burda!
		http.authorizeRequests().antMatchers("/**").authenticated();
		
		http.addFilter(this.getAuthenticationFilter());
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService);

		authenticationFilter.setFilterProcessesUrl("/login");
		
		return authenticationFilter;
	}

	// doğrulama kısmı
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.userDetailsService(this.userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
