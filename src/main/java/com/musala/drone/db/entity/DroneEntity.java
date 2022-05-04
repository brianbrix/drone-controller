package com.musala.drone.db.entity;

import com.musala.drone.enums.ModelEnum;
import com.musala.drone.enums.StateEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
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
    private ModelEnum model;
    @DecimalMax("500.0")
    private Double weight;
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private StateEnum state;
    @OneToMany(mappedBy="drone")
    private Set<MedicationEntity> medications;
    private Boolean available=true;

}
