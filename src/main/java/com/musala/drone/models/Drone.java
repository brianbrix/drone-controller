package com.musala.drone.models;

import com.musala.drone.Validators.Model;

import com.musala.drone.Validators.State;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drone implements Serializable {
    private Long id;
    @Size(max = 100, message = "Serial Number too long")
    private String serialNumber;
    @Model(message = "Invalid Model given")
    private String model;
    @DecimalMax(value = "500.0", message = "Weight can only be upto 500")
    private Double weight;
    @DecimalMax(value = "100.0",message = "Battery Capacity can only be upto 100")
    private Double batteryCapacity;
    @State(message = "Invalid State given")
    private String state;
    private Set<Medication> items = new HashSet<>();

}
