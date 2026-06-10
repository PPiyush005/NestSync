package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.BlockRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityGuardService {

    private final SecurityGuardRepository securityGuardRepository;
    private final BlockRepository blockRepository;
    private final BuildingRepository buildingRepository;

    public SecurityGuardService(SecurityGuardRepository securityGuardRepository,
                                BlockRepository blockRepository,
                                BuildingRepository buildingRepository) {
        this.securityGuardRepository = securityGuardRepository;
        this.blockRepository = blockRepository;
        this.buildingRepository = buildingRepository;
    }

    // Add Guard
    public SecurityGuardResponse addGuard(SecurityGuardRequest request) {
        GuardType guardType;
        try {
            guardType = GuardType.valueOf(request.getGuardType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid guardType: " + request.getGuardType()
                    + ". Valid values: HEAD_SECURITY_GUARD, SECURITY_GUARD");
        }

        SecurityGuard guard = new SecurityGuard();
        guard.setGuardName(request.getGuardName());
        guard.setMobileNo(request.getMobileNo());
        guard.setAddress(request.getAddress());
        guard.setGuardType(guardType);

        if (guardType == GuardType.HEAD_SECURITY_GUARD) {
            if (request.getBlockId() == null)
                throw new RuntimeException("blockId is required for HEAD_SECURITY_GUARD");
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            guard.setBlock(block);
            guard.setBuilding(null);

        } else {
            if (request.getBuildingId() == null)
                throw new RuntimeException("buildingId is required for SECURITY_GUARD");
            Buildings building = buildingRepository.findById(request.getBuildingId())
                    .orElseThrow(() -> new RuntimeException("Building not found with id: " + request.getBuildingId()));
            guard.setBuilding(building);
            guard.setBlock(null);
        }

        SecurityGuard saved = securityGuardRepository.save(guard);
        return toResponse(saved);
    }

    // GetById
    public SecurityGuardResponse getGuardById(Long id) {
        SecurityGuard guard = securityGuardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security Guard not found with id: " + id));
        return toResponse(guard);
    }

    // Get All
    public List<SecurityGuardResponse> getAllGuards() {
        return securityGuardRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Guard Type
    public List<SecurityGuardResponse> getGuardsByType(String type) {
        GuardType guardType;
        try {
            guardType = GuardType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid guardType: " + type
                    + ". Valid values: HEAD_SECURITY_GUARD, SECURITY_GUARD");
        }
        return securityGuardRepository.findByGuardType(guardType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Guards By Block
    public List<SecurityGuardResponse> getGuardsByBlock(Long blockId) {
        blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found with id: " + blockId));
        return securityGuardRepository.findByBlock_BlockId(blockId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Guards By Building
    public List<SecurityGuardResponse> getGuardsByBuilding(Long buildingId) {
        buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        return securityGuardRepository.findByBuilding_BuildingId(buildingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update Guard
    public SecurityGuardResponse updateGuard(Long id, SecurityGuardRequest request) {
        SecurityGuard guard = securityGuardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security Guard not found with id: " + id));

        GuardType guardType;
        try {
            guardType = GuardType.valueOf(request.getGuardType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid guardType: " + request.getGuardType()
                    + ". Valid values: HEAD_SECURITY_GUARD, SECURITY_GUARD");
        }

        guard.setGuardName(request.getGuardName());
        guard.setMobileNo(request.getMobileNo());
        guard.setAddress(request.getAddress());
        guard.setGuardType(guardType);

        if (guardType == GuardType.HEAD_SECURITY_GUARD) {
            if (request.getBlockId() == null)
                throw new RuntimeException("blockId is required for HEAD_SECURITY_GUARD");
            Blocks block = blockRepository.findById(request.getBlockId())
                    .orElseThrow(() -> new RuntimeException("Block not found with id: " + request.getBlockId()));
            guard.setBlock(block);
            guard.setBuilding(null);
        } else {
            if (request.getBuildingId() == null)
                throw new RuntimeException("buildingId is required for SECURITY_GUARD");
            Buildings building = buildingRepository.findById(request.getBuildingId())
                    .orElseThrow(() -> new RuntimeException("Building not found with id: " + request.getBuildingId()));
            guard.setBuilding(building);
            guard.setBlock(null);
        }

        SecurityGuard updated = securityGuardRepository.save(guard);
        return toResponse(updated);
    }

    // Delete Guard
    public String deleteGuard(Long id) {
        SecurityGuard guard = securityGuardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security Guard not found with id: " + id));
        securityGuardRepository.delete(guard);
        return "Security Guard deleted successfully with id: " + id;
    }

    // Helper: Entity -> Response
    private SecurityGuardResponse toResponse(SecurityGuard guard) {
        Long blockId = null;
        String blockName = null;
        Long buildingId = null;
        Integer buildingNo = null;

        if (guard.getBlock() != null) {
            blockId = guard.getBlock().getBlockId();
            blockName = guard.getBlock().getBlockName();
        }
        if (guard.getBuilding() != null) {
            buildingId = guard.getBuilding().getBuildingId();
            buildingNo = guard.getBuilding().getBuildingNo();
        }

        return new SecurityGuardResponse(
                guard.getGuardId(),
                guard.getGuardName(),
                guard.getMobileNo(),
                guard.getAddress(),
                guard.getGuardType(),
                blockId,
                blockName,
                buildingId,
                buildingNo
        );
    }
}