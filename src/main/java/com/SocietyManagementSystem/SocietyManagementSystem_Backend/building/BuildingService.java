package com.SocietyManagementSystem.SocietyManagementSystem_Backend.building;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BlockRepository blockRepository;

    // GetBuildingById
    public BuildingResponse getBuildingById(Long buildingId) {

        Buildings building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building Not Found"));

        return new BuildingResponse(
                building.getBuildingId(),
                building.getBuildingNo(),
                building.getTotalFlats(),
                building.getTotalLift(),
                building.getTotalEntry(),
                building.getTotalExit(),
                building.getBlock().getBlockId(),
                building.getBlock().getBlockName()
        );
    }

    // GetAllBuildings
    public List<BuildingResponse> getAllBuildings() {

        return buildingRepository.findAll()
                .stream()
                .map(building -> new BuildingResponse(
                        building.getBuildingId(),
                        building.getBuildingNo(),
                        building.getTotalFlats(),
                        building.getTotalLift(),
                        building.getTotalEntry(),
                        building.getTotalExit(),
                        building.getBlock().getBlockId(),
                        building.getBlock().getBlockName()
                ))
                .toList();
    }

    // AddBuilding
    public BuildingResponse addBuilding(BuildingRequest buildingRequest) {

        Blocks block = blockRepository.findById(
                        buildingRequest.getBlockId())
                .orElseThrow(() ->
                        new RuntimeException("Block Not Found"));

        Buildings building = new Buildings();

        building.setBuildingNo(buildingRequest.getBuildingNo());
        building.setTotalFlats(buildingRequest.getTotalFlats());
        building.setTotalLift(buildingRequest.getTotalLift());
        building.setTotalEntry(buildingRequest.getTotalEntry());
        building.setTotalExit(buildingRequest.getTotalExit());
        building.setBlock(block);

        Buildings savedBuilding = buildingRepository.save(building);

        return new BuildingResponse(
                savedBuilding.getBuildingId(),
                savedBuilding.getBuildingNo(),
                savedBuilding.getTotalFlats(),
                savedBuilding.getTotalLift(),
                savedBuilding.getTotalEntry(),
                savedBuilding.getTotalExit(),
                block.getBlockId(),
                block.getBlockName()
        );
    }

    // UpdateBuilding
    public BuildingResponse updateBuilding(
            Long buildingId,
            BuildingRequest buildingRequest) {

        Buildings building = buildingRepository.findById(buildingId)
                .orElseThrow(() ->
                        new RuntimeException("Building Not Found"));

        Blocks block = blockRepository.findById(
                        buildingRequest.getBlockId())
                .orElseThrow(() ->
                        new RuntimeException("Block Not Found"));

        building.setBuildingNo(buildingRequest.getBuildingNo());
        building.setTotalFlats(buildingRequest.getTotalFlats());
        building.setTotalLift(buildingRequest.getTotalLift());
        building.setTotalEntry(buildingRequest.getTotalEntry());
        building.setTotalExit(buildingRequest.getTotalExit());
        building.setBlock(block);

        Buildings updatedBuilding = buildingRepository.save(building);

        return new BuildingResponse(
                updatedBuilding.getBuildingId(),
                updatedBuilding.getBuildingNo(),
                updatedBuilding.getTotalFlats(),
                updatedBuilding.getTotalLift(),
                updatedBuilding.getTotalEntry(),
                updatedBuilding.getTotalExit(),
                block.getBlockId(),
                block.getBlockName()
        );
    }

    // DeleteBuilding
    public String deleteBuilding(Long buildingId) {

        Buildings building = buildingRepository.findById(buildingId)
                .orElseThrow(() ->
                        new RuntimeException("Building Not Found"));

        buildingRepository.delete(building);

        return "Building deleted successfully";
    }
}