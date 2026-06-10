package com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerResponse {

    private Long workerId;
    private String workerName;
    private String occupation;

    private List<Long> flatIds;


}
