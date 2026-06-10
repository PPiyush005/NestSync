package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponse {

    private Long complaintId;
    private String title;
    private String description;
    private String category;
    private ComplaintStatus status;
    private LocalDate complaintDate;
    private Long ownerId;
    private String ownerName;
    private Long flatId;
    private String flatAddress;

}