package com.musala.drone.controller;

import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
    @PostMapping("/{droneId}/addItems")
    Drone addDrone(@PathVariable(name = "droneId") Long droneId, @Valid @RequestBody Set<Medication> medicationSet) throws Exception {
        return droneService.loadMedicationItems(droneId,medicationSet);
    }


}
