package com.musala.drone.models;

import com.musala.drone.enums.Model;
import com.musala.drone.enums.State;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drone implements Serializable {
    private Long id;
    @Size(max = 100, message = "Serial Number too long")
    private String serialNumber;
    private Model model;
    @DecimalMax(value = "500.0", message = "Weight too big")
    private Double weight;
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;

}
