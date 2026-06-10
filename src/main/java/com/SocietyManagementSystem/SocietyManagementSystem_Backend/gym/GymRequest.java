package com.SocietyManagementSystem.SocietyManagementSystem_Backend.gym;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymRequest {

    private String gymName;

    private Integer capacity;

    private String openingTime;

    private String closingTime;
}
