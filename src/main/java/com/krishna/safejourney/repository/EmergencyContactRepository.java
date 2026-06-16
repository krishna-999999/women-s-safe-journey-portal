package com.krishna.safejourney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.safejourney.entities.EmergencyContact;
import com.krishna.safejourney.entities.User;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    List<EmergencyContact> findByUser(User user);

    boolean existsByUserAndPhoneNumber(User user, String phoneNumber);

    boolean existsByUserAndEmail(User user, String email);
}