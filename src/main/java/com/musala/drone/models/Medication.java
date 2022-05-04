package com.musala.drone.models;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medication {
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$",message = "Medicine name contains invalid characters")
    private String name;
    private Double weight;
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Code contains invalid characters.")
    private String code;
    private String image;
}
