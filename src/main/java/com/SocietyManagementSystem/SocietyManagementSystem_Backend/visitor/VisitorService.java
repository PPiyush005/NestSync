package com.SocietyManagementSystem.SocietyManagementSystem_Backend.visitor;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final FlatRepository flatRepository;
    private final AuthHelper authHelper;

    public VisitorService(VisitorRepository visitorRepository,
                          FlatRepository flatRepository,
                          AuthHelper authHelper) {
        this.visitorRepository = visitorRepository;
        this.flatRepository = flatRepository;
        this.authHelper = authHelper;
    }

    public VisitorResponse addVisitor(VisitorRequest request) {
        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        validateGuardBuildingAccess(flat.getBuilding().getBuildingId());

        Visitor visitor = new Visitor();
        visitor.setVisitorName(request.getVisitorName());
        visitor.setMobileNo(request.getMobileNo());
        visitor.setPurpose(request.getPurpose());
        visitor.setVisitDate(request.getVisitDate());
        visitor.setEntryTime(request.getEntryTime());
        visitor.setExitTime(null);
        visitor.setFlat(flat);

        return toResponse(visitorRepository.save(visitor));
    }

    private void validateGuardBuildingAccess(Long buildingId) {
        User currentUser = authHelper.getCurrentUser();
        if (currentUser.getRole().name().equals("SECURITY_GUARD")) {
            if (!currentUser.getBuildingId().equals(buildingId)) {
                throw new RuntimeException("Access denied: You can only manage your assigned building");
            }
        }
    }

    // GetById
    public VisitorResponse getVisitorById(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));
        return toResponse(visitor);
    }

    // Get All
    public List<VisitorResponse> getAllVisitors() {
        return visitorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Flat
    public List<VisitorResponse> getVisitorsByFlat(Long flatId) {
        flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + flatId));
        return visitorRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Date
    public List<VisitorResponse> getVisitorsByDate(LocalDate date) {
        return visitorRepository.findByVisitDate(date)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get Currently Inside (no exit time)
    public List<VisitorResponse> getCurrentlyInsideVisitors() {
        return visitorRepository.findByExitTimeIsNull()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Mark Exit Time
    public VisitorResponse markExit(Long id, LocalTime exitTime) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));
        visitor.setExitTime(exitTime);
        Visitor updated = visitorRepository.save(visitor);
        return toResponse(updated);
    }

    // Update Visitor
    public VisitorResponse updateVisitor(Long id, VisitorRequest request) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));

        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        visitor.setVisitorName(request.getVisitorName());
        visitor.setMobileNo(request.getMobileNo());
        visitor.setPurpose(request.getPurpose());
        visitor.setVisitDate(request.getVisitDate());
        visitor.setEntryTime(request.getEntryTime());
        visitor.setExitTime(request.getExitTime());
        visitor.setFlat(flat);

        Visitor updated = visitorRepository.save(visitor);
        return toResponse(updated);
    }

    // Delete Visitor
    public String deleteVisitor(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));
        visitorRepository.delete(visitor);
        return "Visitor deleted successfully with id: " + id;
    }

    // Helper: Entity -> Response
    private VisitorResponse toResponse(Visitor visitor) {
        return new VisitorResponse(
                visitor.getVisitorId(),
                visitor.getVisitorName(),
                visitor.getMobileNo(),
                visitor.getPurpose(),
                visitor.getVisitDate(),
                visitor.getEntryTime(),
                visitor.getExitTime(),
                visitor.getFlat().getFlatId(),
                visitor.getFlat().getFlatAddress()
        );
    }
}