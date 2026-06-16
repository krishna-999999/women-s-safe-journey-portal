package com.krishna.safejourney.dto;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyResponse {

    private String trackingToken;

    private String status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}