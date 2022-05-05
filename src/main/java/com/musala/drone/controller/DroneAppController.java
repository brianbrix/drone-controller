package com.musala.drone.controller;

import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
    @PostMapping(value = "{droneId}/addItem", consumes = {"multipart/form-data"})
    Drone addDrone(@PathVariable(name = "droneId") Long droneId, @RequestParam("name") String name, @RequestParam("weight") Double weight, @RequestParam("code") String code, @RequestParam("file") MultipartFile file) throws Exception {
        var medication = Medication.builder()
                .code(code)
                .name(name)
                .weight(weight)
                .build();

        return droneService.loadMedicationItems(droneId,medication, file);
    }
    @GetMapping(value = "{droneId}/getItems")
    List<Medication> getItems(@PathVariable(name = "droneId") Long droneId)
    {
        return droneService.checkLoadedMedications(droneId);
    }

    @GetMapping(value = "available")
    List<Drone> getAvailable()
    {
        return droneService.checkAvailableDrones();
    }
    @GetMapping
    List<Drone> getAll()
    {
        return droneService.getAllDrones();
    }


}
