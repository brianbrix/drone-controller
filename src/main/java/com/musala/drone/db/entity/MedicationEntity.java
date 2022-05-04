package com.musala.drone.db.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "medications")
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double weight;
    private String code;
    private String image;
    @ManyToOne
    @JoinColumn(name="drone_id", nullable=false)
    private DroneEntity drone;
}
