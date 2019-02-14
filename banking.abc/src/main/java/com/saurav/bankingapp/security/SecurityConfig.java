package com.saurav.bankingapp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
		  	.antMatchers(HttpMethod.POST,"/users").hasAnyAuthority("MANAGER", "OPERATOR", "PREMIUM", "REGULAR")
		  	.antMatchers("/users/id/**").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers("/users/phone/**").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers("/tokens").hasAnyAuthority("MANAGER", "OPERATOR", "PREMIUM", "REGULAR")
		  	.antMatchers("/tokens/counter/**").hasAnyAuthority("MANAGER", "OPERATOR", "PREMIUM", "REGULAR")
		  	.antMatchers("/tokens/{id}/**").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers(HttpMethod.GET, "/services").hasAnyAuthority("MANAGER", "OPERATOR", "PREMIUM", "REGULAR")
		  	.antMatchers(HttpMethod.POST, "/services").hasAnyAuthority("MANAGER", "OPERATOR")
		  	.antMatchers("/services/{id}").hasAnyAuthority("MANAGER", "OPERATOR")
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