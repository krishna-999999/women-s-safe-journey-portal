package com.krishna.safejourney.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;

    @Email
    private String email;

    @NotBlank
    private String relation;
}