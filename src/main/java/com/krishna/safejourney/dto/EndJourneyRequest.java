package com.krishna.safejourney.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndJourneyRequest {

    @NotBlank(message = "Tracking token is required")
    private String trackingToken;
}