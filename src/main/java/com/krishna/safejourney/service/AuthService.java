package com.krishna.safejourney.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krishna.safejourney.config.JwtUtil;
import com.krishna.safejourney.dto.LoginRequest;
import com.krishna.safejourney.dto.OtpRequest;
import com.krishna.safejourney.dto.OtpVerifyRequest;
import com.krishna.safejourney.dto.RegisterRequest;
import com.krishna.safejourney.entities.OtpVerification;
import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.exception.BadRequestException;
import com.krishna.safejourney.exception.ConflictException;
import com.krishna.safejourney.exception.ResourceNotFoundException;
import com.krishna.safejourney.exception.UnauthorizedException;
import com.krishna.safejourney.repository.OtpRepository;
import com.krishna.safejourney.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    // REGISTER
    public String register(RegisterRequest request) {

        // check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new ConflictException("Email already registered");
        }
        
        // check if phone number already exists
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new ConflictException("Phone number already registered");
        }

        // create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();
        
        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
    
    public String sendOtp(OtpRequest request) {

        String email = request.getEmail();

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        otpRepository.deleteByEmail(email);

        OtpVerification otpEntity = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otpEntity);

        // 🔥 SEND EMAIL HERE
        emailService.sendOtpEmail(email, otp);

        return "OTP sent successfully";
    }
    public String verifyOtp(OtpVerifyRequest request) {

        OtpVerification otpData = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expired");
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            throw new BadRequestException("Invalid OTP");
        }

        otpRepository.deleteByEmail(request.getEmail());

        return "Login successful via OTP";
    }
}