# 🚀 SafeJourney Backend

## 📖 Project Overview
SafeJourney is a **Spring Boot-based backend application** designed to enhance user safety during travel.  
It provides real-time journey tracking, emergency alert mechanisms, and contact notification features.

The system ensures that users can securely track their journeys and quickly notify trusted contacts in emergency situations.

---

## 🎯 Problem Statement
Many users lack a reliable system to:
- Share live location during travel
- Notify trusted contacts in emergencies
- Maintain safety tracking during journeys

SafeJourney solves this by providing a **secure, real-time tracking and alert system**.

---

## ✨ Features

### 🔐 Authentication & Security
- User Registration & Login
- JWT-based Authentication
- Stateless session management
- OTP verification via email

---

### 🧭 Journey Management
- Start and End Journey
- Unique Tracking Token generation
- Prevent multiple active journeys per user
- Mandatory emergency contact validation before starting journey

---

### 📍 Location Tracking
- Store real-time location updates
- Retrieve latest location via tracking token
- Supports live tracking scenarios

---

### 🚨 Emergency Alert System
- Trigger alerts during emergencies
- Notify emergency contacts (email-based)
- Alert status tracking

---

### 👥 Emergency Contact Management
- Add / Update / Delete contacts
- Maximum 3 contacts per user
- Prevent duplicate phone/email
- At least one contact required to start a journey

---

## 🏗️ System Architecture
```
Client (Postman / Frontend)
    ↓
Controller Layer
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (JPA)
    ↓
Database
```


---

## 🧠 Backend Design Principles

- Layered Architecture (Controller → Service → Repository)
- Separation of Concerns
- DTO Pattern for request/response
- Centralized Exception Handling
- Secure Authentication using JWT

---

## 🔐 Security Implementation

- Spring Security Integration
- JWT Token-based Authentication
- Custom JWT Filter
- Authentication stored in SecurityContext

### Flow:

Login → Generate JWT → Send in Header → Validate Token → Set User in SecurityContext


---

## ⚙️ Tech Stack

| Technology | Usage |
|-----------|------|
| Java | Core Programming |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database Layer |
| MySQL / H2 | Database |
| Lombok | Boilerplate Reduction |
| Maven | Build Tool |

---

## 📦 API Endpoints

### 🔑 Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/send-otp`
- `POST /api/auth/verify-otp`

---

### 🧭 Journey
- `POST /api/journey/start`
- `POST /api/journey/end`
- `POST /api/journey/alert`
- `GET /api/journey/{token}`

---

### 📍 Location
- `POST /api/location`
- `GET /api/location/{token}`

---

### 👥 Contacts
- `POST /api/contacts`
- `GET /api/contacts`
- `PUT /api/contacts/{id}`
- `DELETE /api/contacts/{id}`

---

## ⚠️ Business Rules

- A user **must have at least one emergency contact** before starting a journey  
- Maximum **3 contacts per user**  
- Duplicate phone/email is not allowed  
- Only **one active journey per user**  
- Only the **owner can modify their data**  

---

## 🛠️ Exception Handling

Centralized exception handling using `@RestControllerAdvice`

### Custom Exceptions

| Exception | HTTP Status |
|----------|------------|
| BadRequestException | 400 |
| UnauthorizedException | 401 |
| ResourceNotFoundException | 404 |
| ConflictException | 409 |

---

### Example Error Response

```json
{
  "message": "Contact not found",
  "status": 404,
  "timestamp": "2026-04-10T10:00:00"
} 
```
## 🔄 Application Flow

```
User Login
   ↓
Receive JWT Token
   ↓
Start Journey (validations applied)
   ↓
Location updates stored
   ↓
Emergency alert triggered (if needed)
   ↓
Contacts notified
```

## Database Entities

- User
- Journey
- LocationHistory
- EmergencyContact

## How to Run the Project
### Prerequisites
- Java 17+
- Maven
- MySQL

## Steps
```
# Clone the repository
git clone https://github.com/your-username/safejourney.git

# Navigate into project
cd safejourney

# Build project
mvn clean install

# Run application
mvn spring-boot:run

```

## Testing
- Use Postman or any API client
- Add JWT token in header:
```
Authorization: Bearer <your_token>
```
## Future Enhancements
- Real-time tracking using WebSockets
- Maps integration
- SMS alerts (Twilio)
- Role-based access control
- Refresh token implementation
- Mobile app integration

##  Author

- Erukula Yugesh
- Contact : **yugesherukula1@gmail.com**


##  Project Highlights
- Real-world problem solving
- Secure API design
- Business rule enforcement
- Global exception handling
- Scalable backend architecture

## Contributions
- Contributions are welcome!
- Feel free to fork the repository and submit pull requests.