package com.krishna.safejourney.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {

        // 🔥 GET USER FROM JWT (already set in JwtFilter)
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestBody User updatedUser,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
        currentUser.setAddress(updatedUser.getAddress());

        userRepository.save(currentUser);

        return ResponseEntity.ok(currentUser);
    }

}
