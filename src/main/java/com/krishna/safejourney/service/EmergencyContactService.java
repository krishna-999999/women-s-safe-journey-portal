package com.krishna.safejourney.service;

import java.util.List;

import com.krishna.safejourney.dto.ContactRequest;
import com.krishna.safejourney.dto.ContactResponse;

public interface EmergencyContactService {

    void addContact(ContactRequest request);

    List<ContactResponse> getMyContacts();

    void updateContact(Long contactId, ContactRequest request);

    void deleteContact(Long contactId);
}