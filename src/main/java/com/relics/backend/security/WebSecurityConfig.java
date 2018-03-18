package com.relics.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.relics.backend.model.UserTypes;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AccessDecisionManager accessDecisionManager;
	
	@Autowired
	FilterInvocationSecurityMetadataSource filterInvocationMetadataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(new CorsFilter(), AbstractPreAuthenticatedProcessingFilter.class);
		http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
				fsi.setAccessDecisionManager(accessDecisionManager);
				fsi.setSecurityMetadataSource(filterInvocationMetadataSource);
				try {
					fsi.afterPropertiesSet();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return fsi;
			}

		});
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole(UserTypes.ManagedTypes.ADMIN.name());
		http.authorizeRequests().antMatchers("/my-profile/**").hasAnyRole(UserTypes.ManagedTypes.USER.name());
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/login")
				.permitAll().anyRequest().authenticated().and()
				// We filter the api/login requests
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// And filter other requests to check the presence of JWT in header
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
	}

}
