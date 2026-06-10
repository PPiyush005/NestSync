package com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatRequest {

    private String flatAddress;

    private Long buildingId;
}
