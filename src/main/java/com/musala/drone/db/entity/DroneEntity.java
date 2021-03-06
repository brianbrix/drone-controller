package com.musala.drone.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.musala.drone.enums.ModelEnum;
import com.musala.drone.enums.StateEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import java.io.Serializable;
import java.util.List;

@Table(name = "drones")
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;
    @Column(length = 100, unique = true)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private ModelEnum model;
    @DecimalMax("500.0")
    private Double weightLimit;
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private StateEnum state;
    @JsonIgnore
    @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicationEntity> items;

}
