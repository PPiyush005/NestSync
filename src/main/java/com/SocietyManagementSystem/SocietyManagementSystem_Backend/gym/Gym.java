package com.SocietyManagementSystem.SocietyManagementSystem_Backend.gym;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gym")
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymId;

    private String gymName;

    private Integer capacity;

    private String openingTime;

    private String closingTime;
}
