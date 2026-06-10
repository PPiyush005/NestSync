package com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerRequest {

    private String workerName;
    private String occupation;
    private Long mobileNo;

    private List<Long> flatIds;
}
