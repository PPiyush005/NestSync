package com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("/all")
    public ResponseEntity<List<WorkerResponse>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerResponse> getWorkerById(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                workerService.getWorkerById(workerId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<WorkerResponse> addWorker(
            @RequestBody WorkerRequest workerRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workerService.addWorker(workerRequest));
    }

    @PutMapping("/update/{workerId}")
    public ResponseEntity<WorkerResponse> updateWorker(
            @PathVariable Long workerId,
            @RequestBody WorkerRequest workerRequest) {

        return ResponseEntity.ok(
                workerService.updateWorker(workerId, workerRequest)
        );
    }

    @DeleteMapping("/delete/{workerId}")
    public ResponseEntity<String> deleteWorker(
            @PathVariable Long workerId) {

        return ResponseEntity.ok(
                workerService.deleteWorker(workerId)
        );
    }

    @PostMapping("/{workerId}/assign-flat/{flatId}")
    public ResponseEntity<WorkerResponse> assignFlatToWorker(
            @PathVariable Long workerId,
            @PathVariable String flatId) {

        return ResponseEntity.ok(
                workerService.assignFlatToWorker(
                        workerId,
                        flatId
                )
        );
    }

    @DeleteMapping("/{workerId}/remove-flat/{flatId}")
    public ResponseEntity<WorkerResponse> removeFlatFromWorker(
            @PathVariable Long workerId,
            @PathVariable String flatId) {

        return ResponseEntity.ok(
                workerService.removeFlatFromWorker(
                        workerId,
                        flatId
                )
        );
    }
}
