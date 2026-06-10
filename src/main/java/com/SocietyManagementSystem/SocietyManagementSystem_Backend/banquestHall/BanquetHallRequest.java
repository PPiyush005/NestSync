package com.SocietyManagementSystem.SocietyManagementSystem_Backend.banquestHall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanquetHallRequest {

    private String hallName;

    private Integer capacity;

    private Double bookingCharge;
}
