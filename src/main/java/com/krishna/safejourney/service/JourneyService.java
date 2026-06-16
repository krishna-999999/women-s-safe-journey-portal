package com.krishna.safejourney.service;

import com.krishna.safejourney.dto.AlertRequest;
import com.krishna.safejourney.dto.EndJourneyRequest;
import com.krishna.safejourney.dto.JourneyResponse;
import com.krishna.safejourney.dto.StartJourneyRequest;

public interface JourneyService {

	JourneyResponse startJourney(StartJourneyRequest request);

	JourneyResponse endJourney(EndJourneyRequest request);

	JourneyResponse triggerAlert(AlertRequest request);

	JourneyResponse getJourneyByToken(String trackingToken);

	boolean hasActiveJourney(Long userId);
}