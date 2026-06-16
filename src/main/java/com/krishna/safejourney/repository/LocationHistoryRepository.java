package com.krishna.safejourney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.safejourney.entities.LocationHistory;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long>{
	
	Optional<LocationHistory> findTopByJourneyIdOrderByRecordedAtDesc(Long journeyId);

}
