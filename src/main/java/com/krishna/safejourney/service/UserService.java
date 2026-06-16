package com.krishna.safejourney.service;

import com.krishna.safejourney.entities.User;

public interface UserService {
	
	public User getByEmail(String email);

}
