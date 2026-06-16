package com.krishna.safejourney.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String passwordHash;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<EmergencyContact> emergencyContacts;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Journey> journeys;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

//@JsonIgnore :
//"Jackson, ignore this field when converting this object to JSON."







//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//cascade:
//EX:userRepository.delete(user); ==>cascade = CascadeType.ALL
//User deleted ✅
//Contacts deleted ✅

//if not used:
	//	userRepository.delete(user);
	//	💥 What happens?
	//
	//	👉 DATABASE WILL STOP YOU
	//
	//	You’ll get something like:
	//	Cannot delete or update a parent row:
	//	a foreign key constraint fails



//orphan Removal
//“If a child is removed from parent → delete it from DB”
//Example: (REMOVE CONTACT)  
//user.getEmergencyContacts().remove(contact);
//userRepository.save(user);
//With: orphanRemoval = true
//Result:
//Contact removed from list ✅
//Contact deleted from DB ✅
//if not used :
	//	Case 1: You DELETE the User
	//	userRepository.delete(user);
	//
	//	👉 Result:
	//
	//	User deleted ✅
	//
	//	Contacts deleted ✅ (because of cascade)
	//
	//	✔ Works fine
	//	❗ orphanRemoval not needed here
	//
	//	💀 Case 2: You REMOVE a contact from list
	//	user.getEmergencyContacts().remove(contact);
	//	userRepository.save(user);
	//	❌ Without orphanRemoval
	//
	//	👉 Result:
	//
	//	Removed from Java list ✅
	//
	//	Still exists in DB ❌
	//
	//	👉 So DB now has:
	//
	//	EmergencyContact → still present
	//	user_id → still pointing to user









//object/relationship binding
//**********Use Unidirectional WHEN:

//You only need access in ONE direction
//Simpler design is enough
//No need to navigate back

//👉 Example:
//User → Roles
//Order → Items (sometimes)

//**********Use Bidirectional WHEN:

//You need navigation BOTH ways
//Real-world relationship is strong
//You need queries from both sides

//👉 Example:
//User ↔ EmergencyContacts
//User ↔ Orders
//Customer ↔ Transactions



