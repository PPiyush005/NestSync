package com.SocietyManagementSystem.SocietyManagementSystem_Backend.banquestHall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanquetHallService {

    @Autowired
    private BanquetHallRepository banquetHallRepository;

    public BanquetHallResponse getHallById(Long hallId) {

        BanquetHall hall = banquetHallRepository.findById(hallId)
                .orElseThrow(() ->
                        new RuntimeException("Banquet Hall Not Found"));

        return new BanquetHallResponse(
                hall.getHallId(),
                hall.getHallName(),
                hall.getCapacity(),
                hall.getBookingCharge()
        );
    }

    public List<BanquetHallResponse> getAllHalls() {

        return banquetHallRepository.findAll()
                .stream()
                .map(hall -> new BanquetHallResponse(
                        hall.getHallId(),
                        hall.getHallName(),
                        hall.getCapacity(),
                        hall.getBookingCharge()
                ))
                .toList();
    }

    public BanquetHallResponse addHall(
            BanquetHallRequest request) {

        BanquetHall hall = new BanquetHall();

        hall.setHallName(request.getHallName());
        hall.setCapacity(request.getCapacity());
        hall.setBookingCharge(request.getBookingCharge());

        BanquetHall savedHall =
                banquetHallRepository.save(hall);

        return new BanquetHallResponse(
                savedHall.getHallId(),
                savedHall.getHallName(),
                savedHall.getCapacity(),
                savedHall.getBookingCharge()
        );
    }

    public BanquetHallResponse updateHall(
            Long hallId,
            BanquetHallRequest request) {

        BanquetHall hall = banquetHallRepository.findById(hallId)
                .orElseThrow(() ->
                        new RuntimeException("Banquet Hall Not Found"));

        hall.setHallName(request.getHallName());
        hall.setCapacity(request.getCapacity());
        hall.setBookingCharge(request.getBookingCharge());

        BanquetHall updatedHall =
                banquetHallRepository.save(hall);

        return new BanquetHallResponse(
                updatedHall.getHallId(),
                updatedHall.getHallName(),
                updatedHall.getCapacity(),
                updatedHall.getBookingCharge()
        );
    }

    public String deleteHall(Long hallId) {

        BanquetHall hall = banquetHallRepository.findById(hallId)
                .orElseThrow(() ->
                        new RuntimeException("Banquet Hall Not Found"));

        banquetHallRepository.delete(hall);

        return "Banquet Hall deleted successfully";
    }
}

