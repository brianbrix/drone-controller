package com.musala.drone.models.resp;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatteryResp {
    private Double batteryCapacity;
}
