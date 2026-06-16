package com.krishna.safejourney.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.krishna.safejourney.dto.ContactRequest;
import com.krishna.safejourney.dto.ContactResponse;
import com.krishna.safejourney.entities.EmergencyContact;
import com.krishna.safejourney.entities.User;
import com.krishna.safejourney.exception.BadRequestException;
import com.krishna.safejourney.exception.ConflictException;
import com.krishna.safejourney.exception.ResourceNotFoundException;
import com.krishna.safejourney.exception.UnauthorizedException;
import com.krishna.safejourney.repository.EmergencyContactRepository;

@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

	private final EmergencyContactRepository contactRepository;

	public EmergencyContactServiceImpl(EmergencyContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public void addContact(ContactRequest request) {

		User user = getUser();

		if (contactRepository.findByUser(user).size() >= 3) {
			throw new BadRequestException("Maximum 3 contacts allowed");
		}

		if (contactRepository.existsByUserAndPhoneNumber(user, request.getPhoneNumber())) {
			throw new ConflictException("Phone number already exists");
		}

		if (request.getEmail() != null && contactRepository.existsByUserAndEmail(user, request.getEmail())) {
			throw new ConflictException("Email already exists");
		}

		EmergencyContact contact = new EmergencyContact();
		contact.setName(request.getName());
		contact.setPhoneNumber(request.getPhoneNumber());
		contact.setEmail(request.getEmail());
		contact.setRelation(request.getRelation());
		contact.setUser(user);

		contactRepository.save(contact);
	}

	@Override
	public List<ContactResponse> getMyContacts() {

		User user = getUser();

		return contactRepository.findByUser(user).stream().map(
				c -> new ContactResponse(c.getId(), c.getName(), c.getPhoneNumber(), c.getEmail(), c.getRelation()))
				.toList();
	}

	@Override
	public void updateContact(Long id, ContactRequest request) {

		User user = getUser();

		EmergencyContact contact = contactRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

		if (!contact.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You are not allowed to modify this contact");
		}

		if (!contact.getPhoneNumber().equals(request.getPhoneNumber())
				&& contactRepository.existsByUserAndPhoneNumber(user, request.getPhoneNumber())) {
			throw new ConflictException("Phone number already registered");
		}

		if (request.getEmail() != null && !request.getEmail().equals(contact.getEmail())
				&& contactRepository.existsByUserAndEmail(user, request.getEmail())) {
			throw new ConflictException("Email already registered");
		}

		contact.setName(request.getName());
		contact.setPhoneNumber(request.getPhoneNumber());
		contact.setEmail(request.getEmail());
		contact.setRelation(request.getRelation());

		contactRepository.save(contact);
	}

	@Override
	public void deleteContact(Long id) {

		User user = getUser();

		EmergencyContact contact = contactRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

		if (!contact.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedException("You are not allowed to delete this contact");
		}

		contactRepository.delete(contact);
	}

	private User getUser() {

		var auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new UnauthorizedException("User not authenticated");
		}

		Object principal = auth.getPrincipal();

		if (!(principal instanceof User)) {
			throw new UnauthorizedException("User not authenticated");
		}

		return (User) auth.getPrincipal();
	}
}