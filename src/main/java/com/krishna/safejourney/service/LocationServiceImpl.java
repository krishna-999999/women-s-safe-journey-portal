package com.krishna.safejourney.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.krishna.safejourney.dto.LocationRequest;
import com.krishna.safejourney.dto.LocationResponse;
import com.krishna.safejourney.entities.EmergencyContact;
import com.krishna.safejourney.entities.Journey;
import com.krishna.safejourney.entities.JourneyStatus;
import com.krishna.safejourney.entities.LocationHistory;
import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.exception.BadRequestException;
import com.krishna.safejourney.exception.ResourceNotFoundException;
import com.krishna.safejourney.exception.UnauthorizedException;
import com.krishna.safejourney.repository.EmergencyContactRepository;
import com.krishna.safejourney.repository.JourneyRepository;
import com.krishna.safejourney.repository.LocationHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final JourneyRepository journeyRepository;
    private final LocationHistoryRepository locationRepository;
    private final EmergencyContactRepository contactRepository;
    private final EmailService emailService;

    @Override
    public void saveLocation(LocationRequest request) {

        // 1. Find journey
        Journey journey = journeyRepository.findByTrackingToken(request.getTrackingToken())
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

//       2. Validate status
//       AUTH CHECK
        User user = getLoggedInUser();
        if (!journey.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to access this journey");
        }

        //STATUS CHECK
        if (journey.getStatus() == JourneyStatus.COMPLETED) {
            throw new BadRequestException("Journey already completed");
        }
        
     //  RATE LIMIT (basic)
        LocationHistory last = locationRepository
                .findTopByJourneyIdOrderByRecordedAtDesc(journey.getId())
                .orElse(null);

        if (last != null &&
                last.getRecordedAt().isAfter(LocalDateTime.now().minusSeconds(5))) {
            throw new BadRequestException("Too many location updates");
        }
        
        // 3. Save location
        LocationHistory location = LocationHistory.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .journey(journey)
                .build();
        sendLocationToContacts(journey, request.getLatitude(), request.getLongitude());

        locationRepository.save(location);
    }

    @Override
    public LocationResponse getLatestLocation(String trackingToken) {

        Journey journey = journeyRepository.findByTrackingToken(trackingToken)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));
        
        User user = getLoggedInUser();
        if (!journey.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to access this journey");
        }
        
        LocationHistory latest = locationRepository
                .findTopByJourneyIdOrderByRecordedAtDesc(journey.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No location data found"));

        

        return LocationResponse.builder()
                .latitude(latest.getLatitude())
                .longitude(latest.getLongitude())
                .recordedAt(latest.getRecordedAt())
                .build();
    }
    
    private void sendLocationToContacts(Journey journey, Double lat, Double lon) {

        String mapLink = "https://www.google.com/maps?q=" + lat + "," + lon;

        String message = "🚨 Safe Journey Update\n\n"
                + "Live Location:\n" + mapLink;

        List<EmergencyContact> contacts =
                contactRepository.findByUser(journey.getUser());

        for (EmergencyContact contact : contacts) {

            if (contact.getEmail() != null) {

                try {
                    emailService.sendAlertEmail(contact.getEmail(), message);
                } catch (Exception e) {
                    System.out.println("Email failed: " + contact.getEmail());
                }
            }
        }
    }
    
    private User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}