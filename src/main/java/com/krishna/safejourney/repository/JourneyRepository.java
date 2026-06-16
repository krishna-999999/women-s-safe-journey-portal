package com.krishna.safejourney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.safejourney.entities.Journey;
import com.krishna.safejourney.entities.JourneyStatus;


@Repository
public interface JourneyRepository extends JpaRepository<Journey , Long>{
	
//	Unique
//	Public-facing (shared with others)
//	Used to track journey status
	Optional<Journey> findByTrackingToken(String trackingToken);
	
	
//	Check if user already has active journey
//	Prevent multiple tracking sessions
	Optional<Journey> findByUserIdAndStatus(Long userId, JourneyStatus status);
	
	boolean existsByUserIdAndStatus(Long userId, JourneyStatus status);


}
