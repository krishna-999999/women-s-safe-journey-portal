package com.krishna.safejourney.service;

import com.krishna.safejourney.dto.LocationRequest;
import com.krishna.safejourney.dto.LocationResponse;

public interface LocationService {

    void saveLocation(LocationRequest request);

    LocationResponse getLatestLocation(String trackingToken);
}