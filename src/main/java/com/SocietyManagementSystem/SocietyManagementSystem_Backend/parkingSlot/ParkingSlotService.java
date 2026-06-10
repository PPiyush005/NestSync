package com.SocietyManagementSystem.SocietyManagementSystem_Backend.parkingSlot;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.BuildingRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final BuildingRepository buildingRepository;
    private final FlatRepository flatRepository;
    private final AuthHelper authHelper;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository,
                              BuildingRepository buildingRepository,
                              FlatRepository flatRepository,
                              AuthHelper authHelper) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.buildingRepository = buildingRepository;
        this.flatRepository = flatRepository;
        this.authHelper = authHelper;
    }

    public ParkingSlotResponse addSlot(ParkingSlotRequest request) {
        ParkingType parkingType = parseParkingType(request.getParkingType());

        Buildings building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + request.getBuildingId()));

        validateGuardBuildingAccess(building.getBuildingId());

        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(request.getSlotNumber());
        slot.setParkingType(parkingType);
        slot.setBuilding(building);
        slot.setVehicleNumber(request.getVehicleNumber());

        if (request.getVehicleNumber() != null && !request.getVehicleNumber().isBlank()) {
            slot.setStatus(ParkingStatus.OCCUPIED);
        } else {
            slot.setStatus(ParkingStatus.AVAILABLE);
        }

        if (parkingType == ParkingType.RESIDENT) {
            if (request.getFlatId() == null)
                throw new RuntimeException("flatId is required for RESIDENT parking");
            Flats flat = flatRepository.findById(request.getFlatId())
                    .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));
            slot.setFlat(flat);
        } else {
            slot.setFlat(null);
        }

        return toResponse(parkingSlotRepository.save(slot));
    }

    private void validateGuardBuildingAccess(Long buildingId) {
        User currentUser = authHelper.getCurrentUser();
        if (currentUser.getRole().name().equals("SECURITY_GUARD")) {
            if (!currentUser.getBuildingId().equals(buildingId)) {
                throw new RuntimeException("Access denied: You can only manage your assigned building");
            }
        }
    }

    // Get By Id
    public ParkingSlotResponse getSlotById(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id: " + id));
        return toResponse(slot);
    }

    // Get All
    public List<ParkingSlotResponse> getAllSlots() {
        return parkingSlotRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Building
    public List<ParkingSlotResponse> getSlotsByBuilding(Long buildingId) {
        buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        return parkingSlotRepository.findByBuilding_BuildingId(buildingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Flat
    public List<ParkingSlotResponse> getSlotsByFlat(Long flatId) {
        flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + flatId));
        return parkingSlotRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Parking Type
    public List<ParkingSlotResponse> getSlotsByType(String type) {
        ParkingType parkingType = parseParkingType(type);
        return parkingSlotRepository.findByParkingType(parkingType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Status
    public List<ParkingSlotResponse> getSlotsByStatus(String status) {
        ParkingStatus parkingStatus = parseParkingStatus(status);
        return parkingSlotRepository.findByStatus(parkingStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Building + Type
    public List<ParkingSlotResponse> getSlotsByBuildingAndType(Long buildingId, String type) {
        buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        ParkingType parkingType = parseParkingType(type);
        return parkingSlotRepository.findByBuilding_BuildingIdAndParkingType(buildingId, parkingType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Available Slots By Building
    public List<ParkingSlotResponse> getAvailableSlotsByBuilding(Long buildingId) {
        buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        return parkingSlotRepository.findByBuilding_BuildingIdAndStatus(buildingId, ParkingStatus.AVAILABLE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Assign Vehicle to Slot (mark OCCUPIED)
    public ParkingSlotResponse assignVehicle(Long slotId, String vehicleNumber) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id: " + slotId));
        if (slot.getStatus() == ParkingStatus.OCCUPIED)
            throw new RuntimeException("Slot " + slot.getSlotNumber() + " is already occupied");
        slot.setVehicleNumber(vehicleNumber);
        slot.setStatus(ParkingStatus.OCCUPIED);
        return toResponse(parkingSlotRepository.save(slot));
    }

    // Remove Vehicle from Slot (mark AVAILABLE)
    public ParkingSlotResponse removeVehicle(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id: " + slotId));
        slot.setVehicleNumber(null);
        slot.setStatus(ParkingStatus.AVAILABLE);
        return toResponse(parkingSlotRepository.save(slot));
    }

    // Update Slot
    public ParkingSlotResponse updateSlot(Long id, ParkingSlotRequest request) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id: " + id));

        ParkingType parkingType = parseParkingType(request.getParkingType());

        Buildings building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + request.getBuildingId()));

        slot.setSlotNumber(request.getSlotNumber());
        slot.setParkingType(parkingType);
        slot.setBuilding(building);
        slot.setVehicleNumber(request.getVehicleNumber());

        if (request.getVehicleNumber() != null && !request.getVehicleNumber().isBlank()) {
            slot.setStatus(ParkingStatus.OCCUPIED);
        } else {
            slot.setStatus(ParkingStatus.AVAILABLE);
        }

        if (parkingType == ParkingType.RESIDENT) {
            if (request.getFlatId() == null)
                throw new RuntimeException("flatId is required for RESIDENT parking");
            Flats flat = flatRepository.findById(request.getFlatId())
                    .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));
            slot.setFlat(flat);
        } else {
            slot.setFlat(null);
        }

        return toResponse(parkingSlotRepository.save(slot));
    }

    // Delete Slot
    public String deleteSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Slot not found with id: " + id));
        parkingSlotRepository.delete(slot);
        return "Parking Slot deleted successfully with id: " + id;
    }

    // Helper: Parse ParkingType
    private ParkingType parseParkingType(String type) {
        try {
            return ParkingType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid parkingType: " + type + ". Valid values: RESIDENT, VISITOR");
        }
    }

    // Helper: Parse ParkingStatus
    private ParkingStatus parseParkingStatus(String status) {
        try {
            return ParkingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status + ". Valid values: AVAILABLE, OCCUPIED");
        }
    }

    // Helper: Entity -> Response
    private ParkingSlotResponse toResponse(ParkingSlot slot) {
        Long flatId = null;
        String flatAddress = null;
        if (slot.getFlat() != null) {
            flatId = slot.getFlat().getFlatId();
            flatAddress = slot.getFlat().getFlatAddress();
        }
        return new ParkingSlotResponse(
                slot.getSlotId(),
                slot.getSlotNumber(),
                slot.getParkingType(),
                slot.getStatus(),
                slot.getVehicleNumber(),
                slot.getBuilding().getBuildingId(),
                slot.getBuilding().getBuildingNo(),
                flatId,
                flatAddress
        );
    }
}