package com.SocietyManagementSystem.SocietyManagementSystem_Backend.complaint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByStatus(ComplaintStatus status);

    List<Complaint> findByFlat_FlatId(Long flatId);  // this is already correct, keep as is

    List<Complaint> findByOwner_OwnerId(Long ownerId);
}