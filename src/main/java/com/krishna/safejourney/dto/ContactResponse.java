package com.krishna.safejourney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String relation;
}