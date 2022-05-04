package com.musala.drone.controller;

import com.musala.drone.models.Drone;
import com.musala.drone.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("drones")
@Log4j2
@RequiredArgsConstructor
public class DroneAppController {
    private final DroneService droneService;
    @PostMapping("/register")
    Drone addDrone(@Valid @RequestBody Drone drone) {
        return droneService.registerDrone(drone);
    }


}
