package com.SocietyManagementSystem.SocietyManagementSystem_Backend.banquestHall;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banquet_hall")
public class BanquetHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hallId;

    private String hallName;

    private Integer capacity;

    private Double bookingCharge;
}
