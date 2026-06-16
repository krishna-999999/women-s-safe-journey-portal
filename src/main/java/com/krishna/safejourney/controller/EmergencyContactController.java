package com.krishna.safejourney.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.safejourney.dto.ContactRequest;
import com.krishna.safejourney.dto.ContactResponse;
import com.krishna.safejourney.service.EmergencyContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
public class EmergencyContactController {

    private final EmergencyContactService contactService;

    public EmergencyContactController(EmergencyContactService contactService) {
        this.contactService = contactService;
    }

    // ADD CONTACT
    @PostMapping("/add")
    public ResponseEntity<String> addContact(
            @Valid @RequestBody ContactRequest request) {

        contactService.addContact(request);
        return ResponseEntity.ok("Contact added");
    }

    // GET ALL CONTACTS (DTO RESPONSE)
    @GetMapping("/getAllMyContacts")
    public ResponseEntity<List<ContactResponse>> getMyContacts() {

        return ResponseEntity.ok(contactService.getMyContacts());
    }

    // UPDATE CONTACT
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactRequest request) {

        contactService.updateContact(id, request);
        return ResponseEntity.ok("Contact updated");
    }

    // DELETE CONTACT
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(
            @PathVariable Long id) {

        contactService.deleteContact(id);
        return ResponseEntity.ok("Contact deleted");
    }
}