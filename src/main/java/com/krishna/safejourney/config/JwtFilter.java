package com.krishna.safejourney.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// to check and validate the token for every request we use this
//This class extends OncePerRequestFilter
//It runs once for every HTTP request before your controller executes.
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		here it need the token Authorization: Bearer <JWT_TOKEN>
		String header = request.getHeader("Authorization");

//		if token is not valid or token is null then skip
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

//		removes the bearer (header in the token)
		String token = header.substring(7);
		
		if (jwtUtil.validateToken(token)) {
//		get the email for the ExtractEmail() method from JwtUtil class
//		check the expirydeatils, payload, signature etc
			String email = jwtUtil.extractEmail(token);

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
					User user = userRepository.findByEmail(email).orElse(null);

					if (user != null) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
								List.of());
						// Now Spring knows:
						// - user is logged in
						// - request is authenticated
						SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		// 🔥 ALWAYS continue filter chain
	    filterChain.doFilter(request, response);
	}
}