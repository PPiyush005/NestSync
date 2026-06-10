package com.SocietyManagementSystem.SocietyManagementSystem_Backend.notice;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.block.Blocks;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;

    @Column(length = 2000)
    private String content;

    private String category; // e.g. GENERAL, MAINTENANCE, EMERGENCY, EVENT

    private LocalDate noticeDate;

    private String postedBy; // e.g. Secretary name or Admin

    @ManyToOne
    @JoinColumn(name = "block_id", nullable = true)
    private Blocks block; // null = society-wide
}