package com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private FlatRepository flatRepository;

    public WorkerResponse getWorkerById(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker Not Found"));

        return new WorkerResponse(
                worker.getWorkerId(),
                worker.getWorkerName(),
                worker.getOccupation(),
                worker.getFlats()
                        .stream()
                        .map(Flats::getFlatId)
                        .toList()
        );
    }

    public List<WorkerResponse> getAllWorkers() {

        return workerRepository.findAll()
                .stream()
                .map(worker -> new WorkerResponse(
                        worker.getWorkerId(),
                        worker.getWorkerName(),
                        worker.getOccupation(),
                        worker.getFlats()
                                .stream()
                                .map(Flats::getFlatId)
                                .toList()
                ))
                .toList();
    }

    public WorkerResponse addWorker(WorkerRequest workerRequest) {

        List<Flats> flats = flatRepository.findAllById(
                workerRequest.getFlatIds());

        Worker worker = new Worker();

        worker.setWorkerName(workerRequest.getWorkerName());
        worker.setOccupation(workerRequest.getOccupation());
        worker.setMobileNo(workerRequest.getMobileNo());
        worker.setFlats(flats);

        Worker savedWorker = workerRepository.save(worker);

        return new WorkerResponse(
                savedWorker.getWorkerId(),
                savedWorker.getWorkerName(),
                savedWorker.getOccupation(),
                savedWorker.getFlats()
                        .stream()
                        .map(Flats::getFlatId)
                        .toList()
        );
    }
    public WorkerResponse updateWorker(
            Long workerId,
            WorkerRequest workerRequest) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker Not Found"));

        worker.setWorkerName(workerRequest.getWorkerName());
        worker.setOccupation(workerRequest.getOccupation());

        Worker updatedWorker =
                workerRepository.save(worker);

        return new WorkerResponse(
                updatedWorker.getWorkerId(),
                updatedWorker.getWorkerName(),
                updatedWorker.getOccupation(),
                updatedWorker.getFlats()
                        .stream()
                        .map(Flats::getFlatId)
                        .toList()
        );
    }

    public String deleteWorker(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker Not Found"));

        workerRepository.delete(worker);

        return "Worker deleted successfully";
    }

    public WorkerResponse assignFlatToWorker(
            Long workerId,
            String flatId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker Not Found"));

        Flats flat = flatRepository.findById(Long.valueOf(flatId))
                .orElseThrow(() ->
                        new RuntimeException("Flat Not Found"));

        worker.getFlats().add(flat);

        Worker updatedWorker = workerRepository.save(worker);

        return new WorkerResponse(
                updatedWorker.getWorkerId(),
                updatedWorker.getWorkerName(),
                updatedWorker.getOccupation(),
                updatedWorker.getFlats()
                        .stream()
                        .map(Flats::getFlatId)
                        .toList()
        );
    }

    public WorkerResponse removeFlatFromWorker(
            Long workerId,
            String flatId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new RuntimeException("Worker Not Found"));

        Flats flat = flatRepository.findById(Long.valueOf(flatId))
                .orElseThrow(() ->
                        new RuntimeException("Flat Not Found"));

        worker.getFlats().remove(flat);

        Worker updatedWorker = workerRepository.save(worker);

        return new WorkerResponse(
                updatedWorker.getWorkerId(),
                updatedWorker.getWorkerName(),
                updatedWorker.getOccupation(),
                updatedWorker.getFlats()
                        .stream()
                        .map(Flats::getFlatId)
                        .toList()
        );
    }
}
