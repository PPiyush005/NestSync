package com.SocietyManagementSystem.SocietyManagementSystem_Backend.securityGuard;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.building.Buildings;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "security_guards")
public class SecurityGuard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guardId;

    private String guardName;

    private String mobileNo;

    private String address;

    @Enumerated(EnumType.STRING)
    private GuardType guardType; // HEAD_SECURITY_GUARD or SECURITY_GUARD

    // For HEAD_SECURITY_GUARD — assigned to a Block
    @ManyToOne
    @JoinColumn(name = "block_id", nullable = true)
    private Blocks block;

    // For SECURITY_GUARD — assigned to a Building
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = true)
    private Buildings building;
}