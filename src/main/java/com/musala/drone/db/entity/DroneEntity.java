package com.musala.drone.db.entity;

import com.musala.drone.enums.Model;
import com.musala.drone.enums.State;
import jakarta.validation.constraints.DecimalMax;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Table(name = "drones")
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    @DecimalMax("500.0")
    private Double weight;
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany(mappedBy="drone")
    private Set<MedicationEntity> medications;

}
