package com.sapeadita.canchitas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsServiceImpl userDetailsImpl;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/","/public/**","/login/**","/assets/**").permitAll().anyRequest().authenticated()
//		.and()
//		.formLogin().loginPage("/login/login").defaultSuccessUrl("/private/principal",true).failureUrl("/login/login?error=true")
//		.loginProcessingUrl("/login/login-post").permitAll()
//		.and()
//		.logout().logoutUrl("/logout").logoutSuccessUrl("/public/inicio");
		
//		http.authorizeRequests().antMatchers("/public/**","/login/**","/assets/**").permitAll().anyRequest().authenticated()
//		.and()
//		.formLogin().loginPage("/login/login").defaultSuccessUrl("/private/principal",true).failureUrl("/login/login?error=true")
//		.loginProcessingUrl("/login/login-post").permitAll()
//		.and()
//		.logout().logoutUrl("/logout").logoutSuccessUrl("/public/inicio");
//		http.authorizeRequests().antMatchers("/","/public/**","/login/**","/assets/**").permitAll().anyRequest().authenticated()
		http.authorizeRequests().antMatchers("/","/login/**","/assets/**").permitAll().anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login/login").defaultSuccessUrl("/private/principal",true).failureUrl("/login/login?error=true")
		.loginProcessingUrl("/login/login-post").permitAll()
		.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}


}
