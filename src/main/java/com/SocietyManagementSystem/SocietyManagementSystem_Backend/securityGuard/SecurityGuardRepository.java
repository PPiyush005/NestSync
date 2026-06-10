package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityGuardRepository extends JpaRepository<SecurityGuard, Long> {

    List<SecurityGuard> findByGuardType(GuardType guardType);

    List<SecurityGuard> findByBlock_BlockId(Long blockId);

    List<SecurityGuard> findByBuilding_BuildingId(Long buildingId);
}