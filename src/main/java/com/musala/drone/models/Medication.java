package com.musala.drone.models;

import lombok.*;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medication {
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$",message = "Medication name contains invalid characters.Allowed(aphanumerics, dash and underscore)")
    private String name;
    private Double weight;
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Code contains invalid characters.Allowed(Uppercase aphanumerics and underscore)")
    private String code;
    private String image;
}
