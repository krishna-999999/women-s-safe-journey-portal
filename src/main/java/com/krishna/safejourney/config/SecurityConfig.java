package com.krishna.safejourney.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	// password hashing
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Allow all APIs for now (temporary)
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//	        http
//	        .cors(c->c.disable())
//	            .csrf(csrf -> csrf.disable())
//	            .authorizeHttpRequests(auth -> auth
//	            		.requestMatchers(
//	            			    "/api/auth/**",
//	            			    "/swagger-ui/**",
//	            			    "/swagger-ui.html",
//	            			    "/v3/api-docs/**"
//	            			).permitAll()
//	            			.requestMatchers("/api/contacts/**").authenticated() // 🔥 FIX
//	            			.requestMatchers("/api/location/**").permitAll()
//	            			.anyRequest().authenticated()
//	            )  
	//// We use addFilterBefore(jwtFilter,
	/// UsernamePasswordAuthenticationFilter.class) / to ensure the JWT is validated
	/// before Spring tries to authenticate/authorize the request
//	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//	        
//	        return http.build();
//	 }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(withDefaults()).csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/api/journey/**").permitAll() // ✅ TEMP
																											// FIX
				.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll().anyRequest()
				.authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
