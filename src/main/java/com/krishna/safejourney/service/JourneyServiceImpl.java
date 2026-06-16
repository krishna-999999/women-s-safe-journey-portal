package com.krishna.safejourney.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.krishna.safejourney.dto.AlertRequest;
import com.krishna.safejourney.dto.EndJourneyRequest;
import com.krishna.safejourney.dto.JourneyResponse;
import com.krishna.safejourney.dto.StartJourneyRequest;
import com.krishna.safejourney.entities.EmergencyContact;
import com.krishna.safejourney.entities.Journey;
import com.krishna.safejourney.entities.JourneyStatus;
import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.exception.BadRequestException;
import com.krishna.safejourney.exception.ConflictException;
import com.krishna.safejourney.exception.ResourceNotFoundException;
import com.krishna.safejourney.exception.UnauthorizedException;
import com.krishna.safejourney.repository.EmergencyContactRepository;
import com.krishna.safejourney.repository.JourneyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JourneyServiceImpl implements JourneyService {

	private final JourneyRepository journeyRepository;
	private final EmergencyContactRepository contactRepository;
	private final EmailService emailService;

	// 🔥 Base URL (avoid hardcoding localhost)
	@Value("${app.base-url}")
	private String baseUrl;

	// ================= START JOURNEY =================
	@Override
	public JourneyResponse startJourney(StartJourneyRequest request) {

		// 1. Validate user
		User user = getLoggedInUser();
		
		// RULE: must have at least one contact
	    if (contactRepository.findByUser(user).isEmpty()) {
	        throw new BadRequestException(
	            "Add at least one emergency contact before starting a journey"
	        );
	    }

		// 2. Prevent multiple active journeys
		if (journeyRepository.findByUserIdAndStatus(user.getId(), JourneyStatus.ONGOING).isPresent()) {
			throw new ConflictException("Active journey already exists");
		}

		// 3. Generate unique tracking token
		String token = UUID.randomUUID().toString();

		// 4. Create new journey object
		Journey journey = Journey.builder().trackingToken(token).status(JourneyStatus.ONGOING).user(user)
				.startLocation(request.getStartLocation()).endLocation(request.getEndLocation())
				.startTime(LocalDateTime.now()) // 🔥 important
				.build();

		// 5. Save journey
		Journey saved = journeyRepository.save(journey);

		return map(saved);
	}

	// ================= END JOURNEY =================
	@Override
	public JourneyResponse endJourney(EndJourneyRequest request) {

		// 1. Fetch journey using token
		Journey journey = getValidJourney(request.getTrackingToken());

		// 2. Update journey status
		journey.setStatus(JourneyStatus.COMPLETED);
		journey.setEndTime(LocalDateTime.now());

		// 4. Save updated journey
		Journey saved = journeyRepository.save(journey);

		return map(saved);
	}

	// ================= TRIGGER ALERT =================
	@Override
	public JourneyResponse triggerAlert(AlertRequest request) {

		// 1. Fetch journey
		Journey journey = getValidJourney(request.getTrackingToken());

		// 2. Validate journey state
		if (journey.getStatus() == JourneyStatus.ALERT) {
			throw new BadRequestException("Alert already active");
		}

		// 3. Update status to ALERT
		journey.setStatus(JourneyStatus.ALERT);

		// 4. Get user and contacts

		List<EmergencyContact> contacts = contactRepository.findByUser(journey.getUser());

		// 🔥 Ensure contacts exist
		if (contacts.isEmpty()) {
			throw new BadRequestException("No emergency contacts found");
		}

		// 5. Build tracking link
		String link = baseUrl + "/api/location/" + journey.getTrackingToken();

		// 7. Send alert to all contacts
		for (EmergencyContact contact : contacts) {

			// 🔥 Skip if email is null
			if (contact.getEmail() != null) {

				try {
					emailService.sendAlertEmail(contact.getEmail(), link);
				} catch (Exception e) {
					// ignore email failure (can improve later with logging)
				}
			}
		}

		// 8. Save updated journey
		Journey saved = journeyRepository.save(journey);

		return map(saved);
	}
	
	@Override
	public JourneyResponse getJourneyByToken(String trackingToken) {
		return map(journeyRepository.findByTrackingToken(trackingToken)
									.orElseThrow(() -> new ResourceNotFoundException("Journey not found")));
	}

	// ================= GET JOURNEY =================
	// COMMON AUTH CHECK
	private Journey getValidJourney(String token) {

		Journey journey = journeyRepository.findByTrackingToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Not found"));

		User user = getLoggedInUser();

		if (!journey.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You are not allowed to access this journey");
		}

		return journey;
	}

	private User getLoggedInUser() {

	    Object principal = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getPrincipal();

	    if (principal instanceof User) {
	        return (User) principal;
	    }

	    throw new RuntimeException("User not authenticated properly");
	}

	// ================= MAPPER =================
	private JourneyResponse map(Journey j) {
		return JourneyResponse.builder().trackingToken(j.getTrackingToken()).status(j.getStatus().name())
				.startTime(j.getStartTime()).endTime(j.getEndTime()).build();
	}
	@Override
	public boolean hasActiveJourney(Long userId) {
	    return journeyRepository
	            .existsByUserIdAndStatus(userId, JourneyStatus.ONGOING);
	}

	
}