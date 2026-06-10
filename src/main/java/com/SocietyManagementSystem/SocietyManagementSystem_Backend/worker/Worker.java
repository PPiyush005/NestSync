package com.SocietyManagementSystem.SocietyManagementSystem_Backend.worker;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.flat.Flats;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    private String workerName;

    private String occupation;

    private Long mobileNo;

    @ManyToMany
    @JoinTable(
            name = "worker_flat",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "flat_id")
    )
    private List<Flats> flats;
}
