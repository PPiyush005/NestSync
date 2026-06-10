package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner.Owner;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner.OwnerRepository;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.AuthHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final OwnerRepository ownerRepository;
    private final FlatRepository flatRepository;
    private final AuthHelper authHelper;

    public ComplaintService(ComplaintRepository complaintRepository,
                            OwnerRepository ownerRepository,
                            FlatRepository flatRepository,
                            AuthHelper authHelper) {
        this.complaintRepository = complaintRepository;
        this.ownerRepository = ownerRepository;
        this.flatRepository = flatRepository;
        this.authHelper = authHelper;
    }

    // Add Complaint
    public ComplaintResponse addComplaint(ComplaintRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + request.getOwnerId()));

        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setComplaintDate(request.getComplaintDate());
        complaint.setStatus(ComplaintStatus.PENDING); // always starts as PENDING
        complaint.setOwner(owner);
        complaint.setFlat(flat);

        Complaint saved = complaintRepository.save(complaint);
        return toResponse(saved);
    }

    // RESIDENT — view only own flat's complaints
    public List<ComplaintResponse> getMyComplaints() {
        Long flatId = authHelper.getCurrentUserFlatId();
        return complaintRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Id
    public ComplaintResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
        return toResponse(complaint);
    }

    // Get All
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Status
    public List<ComplaintResponse> getComplaintsByStatus(String status) {
        ComplaintStatus complaintStatus;
        try {
            complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status + ". Valid values: PENDING, IN_PROGRESS, RESOLVED, REJECTED");
        }
        return complaintRepository.findByStatus(complaintStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Flat
    public List<ComplaintResponse> getComplaintsByFlat(Long flatId) {
        flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + flatId));
        return complaintRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get By Owner
    public List<ComplaintResponse> getComplaintsByOwner(Long ownerId) {
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));
        return complaintRepository.findByOwner_OwnerId(ownerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update Status Only
    public ComplaintResponse updateComplaintStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));

        ComplaintStatus complaintStatus;
        try {
            complaintStatus = ComplaintStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status + ". Valid values: PENDING, IN_PROGRESS, RESOLVED, REJECTED");
        }

        complaint.setStatus(complaintStatus);
        Complaint updated = complaintRepository.save(complaint);
        return toResponse(updated);
    }

    // Update Complaint Details
    public ComplaintResponse updateComplaint(Long id, ComplaintRequest request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + request.getOwnerId()));

        Flats flat = flatRepository.findById(request.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found with id: " + request.getFlatId()));

        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setComplaintDate(request.getComplaintDate());
        complaint.setOwner(owner);
        complaint.setFlat(flat);

        Complaint updated = complaintRepository.save(complaint);
        return toResponse(updated);
    }

    // Delete Complaint
    public String deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
        complaintRepository.delete(complaint);
        return "Complaint deleted successfully with id: " + id;
    }

    // Helper: Entity -> Response
    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(
                complaint.getComplaintId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getComplaintDate(),
                complaint.getOwner().getOwnerId(),
                complaint.getOwner().getOwnerName(),
                complaint.getFlat() != null ? complaint.getFlat().getFlatId() : null,
                complaint.getFlat() != null ? complaint.getFlat().getFlatAddress() : null
        );
    }
}