package com.saurav.bankingapp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BankingAppUserDetailsService appUserDetailsService;	
	@Autowired
	private BankingAppAuthenticationEntryPoint appAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests()
		  	.antMatchers("/users/**").hasAnyAuthority("MANAGER", "OPERATOR", "PREMIUM", "REGULAR")
		  	.antMatchers("/tokens/**").hasAnyAuthority("MANAGER", "OPERATOR","PREMIUM", "REGULAR")
		  	.antMatchers("/tokens/{id}/complete").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers("/tokens/{id}/cancel").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers("/services/**").hasAnyAuthority("MANAGER", "OPERATOR","PREMIUM", "REGULAR")
		  	.antMatchers("/counters/**").hasAnyAuthority("MANAGER", "OPERATOR")
			.and().httpBasic().realmName("MY APP REALM")
			.authenticationEntryPoint(appAuthenticationEntryPoint);
	} 
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
	}
}