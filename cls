[33mcommit 0e23103fc8fa26f50b3c2fe901edeb961339b8ba[m[33m ([m[1;36mHEAD[m[33m -> [m[1;32mmain[m[33m, [m[1;31morigin/main[m[33m)[m
Author: Yugesherukula0 <yugesherukala@gmail.com>
Date:   Sat Apr 11 17:17:18 2026 +0530

    Version2.1

M	src/main/java/com/yugesh/safejourney/service/EmergencyContactServiceImpl.java
M	src/main/java/com/yugesh/safejourney/service/JourneyServiceImpl.java

[33mcommit 7b36d653c2b1c945e9ba480f26d03560cb4a4539[m
Author: Yugesherukula0 <yugesherukala@gmail.com>
Date:   Sat Apr 11 09:15:38 2026 +0530

    updated_version2.0

M	pom.xml
A	src/main/java/com/yugesh/safejourney/config/JwtFilter.java
A	src/main/java/com/yugesh/safejourney/config/JwtUtil.java
M	src/main/java/com/yugesh/safejourney/config/SecurityConfig.java
A	src/main/java/com/yugesh/safejourney/controller/EmergencyContactController.java
A	src/main/java/com/yugesh/safejourney/controller/JourneyController.java
A	src/main/java/com/yugesh/safejourney/controller/LocationController.java
A	src/main/java/com/yugesh/safejourney/dto/AlertRequest.java
A	src/main/java/com/yugesh/safejourney/dto/ContactRequest.java
A	src/main/java/com/yugesh/safejourney/dto/ContactResponse.java
A	src/main/java/com/yugesh/safejourney/dto/EndJourneyRequest.java
A	src/main/java/com/yugesh/safejourney/dto/ErrorResponse.java
A	src/main/java/com/yugesh/safejourney/dto/JourneyResponse.java
A	src/main/java/com/yugesh/safejourney/dto/LocationRequest.java
A	src/main/java/com/yugesh/safejourney/dto/LocationResponse.java
A	src/main/java/com/yugesh/safejourney/dto/StartJourneyRequest.java
M	src/main/java/com/yugesh/safejourney/entities/EmergencyContact.java
M	src/main/java/com/yugesh/safejourney/entities/Journey.java
M	src/main/java/com/yugesh/safejourney/entities/JourneyStatus.java
M	src/main/java/com/yugesh/safejourney/entities/LocationHistory.java
A	src/main/java/com/yugesh/safejourney/exception/BadRequestException.java
A	src/main/java/com/yugesh/safejourney/exception/ConflictException.java
M	src/main/java/com/yugesh/safejourney/exception/GlobalExceptionHandler.java
A	src/main/java/com/yugesh/safejourney/exception/UnauthorizedException.java
M	src/main/java/com/yugesh/safejourney/repository/EmergencyContactRepository.java
M	src/main/java/com/yugesh/safejourney/repository/JourneyRepository.java
M	src/main/java/com/yugesh/safejourney/repository/LocationHistoryRepository.java
M	src/main/java/com/yugesh/safejourney/service/AuthService.java
M	src/main/java/com/yugesh/safejourney/service/EmailService.java
A	src/main/java/com/yugesh/safejourney/service/EmergencyContactService.java
A	src/main/java/com/yugesh/safejourney/service/EmergencyContactServiceImpl.java
A	src/main/java/com/yugesh/safejourney/service/JourneyService.java
A	src/main/java/com/yugesh/safejourney/service/JourneyServiceImpl.java
A	src/main/java/com/yugesh/safejourney/service/LocationService.java
A	src/main/java/com/yugesh/safejourney/service/LocationServiceImpl.java
M	src/main/resources/application.properties

[33mcommit 4113bc24e1e7da7b1f28da96af6d859708b65c3d[m
Author: Yugesherukula0 <yugesherukala@gmail.com>
Date:   Fri Mar 20 11:27:11 2026 +0530

    firstcommit

A	.gitattributes
A	.gitignore
A	.mvn/wrapper/maven-wrapper.properties
A	mvnw
A	mvnw.cmd
A	pom.xml
A	src/main/java/com/yugesh/safejourney/SafeJourneyApplication.java
A	src/main/java/com/yugesh/safejourney/config/SecurityConfig.java
A	src/main/java/com/yugesh/safejourney/controller/AuthController.java
A	src/main/java/com/yugesh/safejourney/dto/LoginRequest.java
A	src/main/java/com/yugesh/safejourney/dto/OtpRequest.java
A	src/main/java/com/yugesh/safejourney/dto/OtpVerifyRequest.java
A	src/main/java/com/yugesh/safejourney/dto/RegisterRequest.java
A	src/main/java/com/yugesh/safejourney/entities/EmergencyContact.java
A	src/main/java/com/yugesh/safejourney/entities/Journey.java
A	src/main/java/com/yugesh/safejourney/entities/JourneyStatus.java
A	src/main/java/com/yugesh/safejourney/entities/LocationHistory.java
A	src/main/java/com/yugesh/safejourney/entities/OtpVerification.java
A	src/main/java/com/yugesh/safejourney/entities/User.java
A	src/main/java/com/yugesh/safejourney/exception/GlobalExceptionHandler.java
A	src/main/java/com/yugesh/safejourney/exception/ResourceNotFoundException.java
A	src/main/java/com/yugesh/safejourney/repository/EmergencyContactRepository.java
A	src/main/java/com/yugesh/safejourney/repository/JourneyRepository.java
A	src/main/java/com/yugesh/safejourney/repository/LocationHistoryRepository.java
A	src/main/java/com/yugesh/safejourney/repository/OtpRepository.java
A	src/main/java/com/yugesh/safejourney/repository/UserRepository.java
A	src/main/java/com/yugesh/safejourney/service/AuthService.java
A	src/main/java/com/yugesh/safejourney/service/EmailService.java
A	src/main/java/com/yugesh/safejourney/service/UserService.java
A	src/main/java/com/yugesh/safejourney/service/UserServiceImpl.java
A	src/main/resources/application.properties
A	src/test/java/com/yugesh/safejourney/SafeJourneyApplicationTests.java
