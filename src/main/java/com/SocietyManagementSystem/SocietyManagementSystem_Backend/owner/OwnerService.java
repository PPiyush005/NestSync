package com.SocietyManagementSystem.SocietyManagementSystem_Backend.owner;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private FlatRepository flatRepository;

    public OwnerResponse getOwnerById(Long ownerId) {

        Owner savedOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new RuntimeException("Owner Not Found"));

        return new OwnerResponse(
                savedOwner.getOwnerId(),
                savedOwner.getOwnerName(),
                savedOwner.getOwnerMobileNo(),
                savedOwner.getOwnerProof(),
                savedOwner.getFlat().getFlatId(),
                savedOwner.getFlat().getFlatAddress()
        );
    }

    public List<OwnerResponse> getAllOwners() {

        return ownerRepository.findAll()
                .stream()
                .map(owner -> new OwnerResponse(
                        owner.getOwnerId(),
                        owner.getOwnerName(),
                        owner.getOwnerMobileNo(),
                        owner.getOwnerProof(),
                        owner.getFlat().getFlatId(),
                        owner.getFlat().getFlatAddress()
                ))
                .toList();
    }

    public OwnerResponse addOwner(OwnerRequest ownerRequest) {

        Flats flat = flatRepository.findById(ownerRequest.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat Not Found"));

        Owner owner = new Owner();

        owner.setOwnerName(ownerRequest.getOwnerName());
        owner.setOwnerMobileNo(ownerRequest.getOwnerMobileNo());
        owner.setOwnerProof(ownerRequest.getOwnerProof());
        owner.setFlat(flat);
        flat.setOwner(owner);

        Owner savedOwner = ownerRepository.save(owner);

        return new OwnerResponse(
                savedOwner.getOwnerId(),
                savedOwner.getOwnerName(),
                savedOwner.getOwnerMobileNo(),
                savedOwner.getOwnerProof(),
                savedOwner.getFlat().getFlatId(),
                savedOwner.getFlat().getFlatAddress()
        );
    }

    public OwnerResponse updateOwner(
            Long ownerId,
            OwnerRequest ownerRequest) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new RuntimeException("Owner Not Found"));

        Flats flat = flatRepository.findById(ownerRequest.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));


        owner.setOwnerName(ownerRequest.getOwnerName());
        owner.setOwnerMobileNo(ownerRequest.getOwnerMobileNo());
        owner.setOwnerProof(ownerRequest.getOwnerProof());
        owner.setFlat(flat);
        flat.setOwner(owner);

        Owner updatedOwner = ownerRepository.save(owner);

        return new OwnerResponse(
                updatedOwner.getOwnerId(),
                updatedOwner.getOwnerName(),
                updatedOwner.getOwnerMobileNo(),
                updatedOwner.getOwnerProof(),
                updatedOwner.getFlat().getFlatId(),
                updatedOwner.getFlat().getFlatAddress()
        );
    }

    @Transactional
    public String deleteOwner(Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new RuntimeException("Owner Not Found"));

        Flats flat = owner.getFlat();

        if (flat != null) {
            flat.setOwner(null);
        }

        owner.setFlat(null);

        ownerRepository.delete(owner);

        return "Owner deleted successfully";
    }
}
