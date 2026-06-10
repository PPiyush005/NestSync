package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ComplaintRequest {

    private String title;
    private String description;
    private String category;
    private LocalDate complaintDate;
    private Long ownerId;
    private Long flatId;
}