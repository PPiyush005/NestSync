package com.SocietyManagementSystem.SocietyManagementSystem_Backend.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymService {

    @Autowired
    private GymRepository gymRepository;

    public GymResponse getGymById(Long gymId) {

        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() ->
                        new RuntimeException("Gym Not Found"));

        return new GymResponse(
                gym.getGymId(),
                gym.getGymName(),
                gym.getCapacity(),
                gym.getOpeningTime(),
                gym.getClosingTime()
        );
    }

    public List<GymResponse> getAllGyms() {

        return gymRepository.findAll()
                .stream()
                .map(gym -> new GymResponse(
                        gym.getGymId(),
                        gym.getGymName(),
                        gym.getCapacity(),
                        gym.getOpeningTime(),
                        gym.getClosingTime()
                ))
                .toList();
    }

    public GymResponse addGym(GymRequest gymRequest) {

        Gym gym = new Gym();

        gym.setGymName(gymRequest.getGymName());
        gym.setCapacity(gymRequest.getCapacity());
        gym.setOpeningTime(gymRequest.getOpeningTime());
        gym.setClosingTime(gymRequest.getClosingTime());

        Gym savedGym = gymRepository.save(gym);

        return new GymResponse(
                savedGym.getGymId(),
                savedGym.getGymName(),
                savedGym.getCapacity(),
                savedGym.getOpeningTime(),
                savedGym.getClosingTime()
        );
    }

    public GymResponse updateGym(
            Long gymId,
            GymRequest gymRequest) {

        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() ->
                        new RuntimeException("Gym Not Found"));

        gym.setGymName(gymRequest.getGymName());
        gym.setCapacity(gymRequest.getCapacity());
        gym.setOpeningTime(gymRequest.getOpeningTime());
        gym.setClosingTime(gymRequest.getClosingTime());

        Gym updatedGym = gymRepository.save(gym);

        return new GymResponse(
                updatedGym.getGymId(),
                updatedGym.getGymName(),
                updatedGym.getCapacity(),
                updatedGym.getOpeningTime(),
                updatedGym.getClosingTime()
        );
    }

    public String deleteGym(Long gymId) {

        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() ->
                        new RuntimeException("Gym Not Found"));

        gymRepository.delete(gym);

        return "Gym deleted successfully";
    }
}
