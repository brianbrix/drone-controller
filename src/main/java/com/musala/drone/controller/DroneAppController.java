package com.musala.drone.controller;

import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.models.resp.BatteryResp;
import com.musala.drone.services.DroneService;
import com.musala.drone.utils.ValidList;
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

    /**
     * This method is to be used incase we want to submit the image as Multipart formdata
     * @param droneId
     * @param name
     * @param weight
     * @param code
     * @param file
     * @return
     * @throws Exception
     */
    @Deprecated
    @PostMapping(value = "{droneId}/addItem1", consumes = {"multipart/form-data"})
    Drone addDrone1(@PathVariable(name = "droneId") Long droneId, @RequestParam("name") String name, @RequestParam("weight") Double weight, @RequestParam("code") String code, @RequestParam("file") MultipartFile file) throws Exception {
        var medication = Medication.builder()
                .code(code)
                .name(name)
                .weight(weight)
                .build();

        return droneService.loadMedicationItem(droneId,medication, file);
    }

    /**
     * This method allows us to add Medication items to a drone + image as a URL to image location
     * @param droneId
     * @param medications
     * @return
     * @throws Exception
     */
    @PostMapping(value = "{droneId}/addItems")
    Drone addItems(@PathVariable Long droneId,  @RequestBody @Valid ValidList<Medication> medications) throws Exception {
        return droneService.loadMedicationItemsList(droneId,medications);
    }

    /**
     * Controller to return loaded medicines for drone
     * @param droneId
     * @return
     */
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

    @PutMapping("update/{droneId}")
    Drone update(@PathVariable Long droneId, @RequestBody Drone drone)
    {
        return droneService.editDrone(droneId, drone);
    }

    @GetMapping("battery/{droneId}")
    BatteryResp getBatteryCapacity(@PathVariable Long droneId)
    {
        return droneService.checkBatteryCapacity(droneId);
    }
}
