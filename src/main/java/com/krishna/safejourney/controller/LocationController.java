package com.krishna.safejourney.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.safejourney.dto.LocationRequest;
import com.krishna.safejourney.dto.LocationResponse;
import com.krishna.safejourney.service.LocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping(consumes="application/json")
    public ResponseEntity<String> saveLocation(@RequestBody @Valid LocationRequest request) {
        locationService.saveLocation(request);
        return ResponseEntity.ok("Location saved successfully");
    }

    @GetMapping("/{token}")
    public ResponseEntity<LocationResponse> getLatestLocation(@PathVariable String token) {
        return ResponseEntity.ok(locationService.getLatestLocation(token));
    }
}
