package com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    // Get Flat By Id
    public FlatResponse getFlatById(String flatId) {

        Flats savedFlat = (Flats) flatRepository.findById(Long.valueOf(flatId))
                .orElseThrow(() -> new RuntimeException("Flat Not Found"));

        return new FlatResponse(
                savedFlat.getFlatId(),
                savedFlat.getFlatAddress(),
                savedFlat.getBuilding().getBuildingId(),
                savedFlat.getBuilding().getBuildingNo(),
                savedFlat.getOwner() != null ? savedFlat.getOwner().getOwnerId() : null,
                savedFlat.getOwner() != null ? savedFlat.getOwner().getOwnerName() : null
        );
    }

    // Get All Flats
    public List<FlatResponse> getAllFlats() {

        return flatRepository.findAll()
                .stream()
                .map(flat -> new FlatResponse(
                        flat.getFlatId(),
                        flat.getFlatAddress(),
                        flat.getBuilding().getBuildingId(),
                        flat.getBuilding().getBuildingNo(),
                        flat.getOwner() != null ? flat.getOwner().getOwnerId() : null,
                        flat.getOwner() != null ? flat.getOwner().getOwnerName() : null
                ))
                .toList();
    }

    // Add Flat
    public FlatResponse addFlat(FlatRequest flatRequest) {

        Buildings building = buildingRepository.findById(
                        flatRequest.getBuildingId())
                .orElseThrow(() ->
                        new RuntimeException("Building Not Found"));

        Flats flat = new Flats();

        flat.setFlatAddress(flatRequest.getFlatAddress());
        flat.setBuilding(building);

        Flats savedFlat = flatRepository.save(flat);

        return new FlatResponse(
                savedFlat.getFlatId(),
                savedFlat.getFlatAddress(),
                savedFlat.getBuilding().getBuildingId(),
                savedFlat.getBuilding().getBuildingNo(),
                savedFlat.getOwner() != null ? savedFlat.getOwner().getOwnerId() : null,
                savedFlat.getOwner() != null ? savedFlat.getOwner().getOwnerName() : null
        );
    }

    // Update Flat
    public FlatResponse updateFlat(
            String flatId,
            FlatRequest flatRequest) {

        Flats flat =  flatRepository.findById(Long.valueOf(flatId))
                .orElseThrow(() ->
                        new RuntimeException("Flat Not Found"));

        Buildings building = buildingRepository.findById(
                        flatRequest.getBuildingId())
                .orElseThrow(() ->
                        new RuntimeException("Building Not Found"));

        flat.setFlatAddress(flatRequest.getFlatAddress());
        flat.setBuilding(building);

        Flats updatedFlat = flatRepository.save(flat);

        return new FlatResponse(
                updatedFlat.getFlatId(),
                updatedFlat.getFlatAddress(),
                updatedFlat.getBuilding().getBuildingId(),
                updatedFlat.getBuilding().getBuildingNo(),
                updatedFlat.getOwner() != null ? updatedFlat.getOwner().getOwnerId() : null,
                updatedFlat.getOwner() != null ? updatedFlat.getOwner().getOwnerName() : null
        );
    }

    // Delete Flat
    public String deleteFlat(String flatId) {

        Flats flat = flatRepository.findById(Long.valueOf(flatId))
                .orElseThrow(() ->
                        new RuntimeException("Flat Not Found"));

        flatRepository.delete(flat);

        return "Flat deleted successfully";
    }
}