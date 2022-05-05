package com.musala.drone.services;

import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.models.resp.BatteryResp;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public interface DroneService {
    Drone registerDrone(Drone drone);
    Drone loadMedicationItems(Long droneId, Medication medicationSet, MultipartFile file) throws Exception;
    List<Medication> checkLoadedMedications(Long droneId);
    List<Drone> checkAvailableDrones();
    List<Drone> getAllDrones();
    BatteryResp checkBatteryCapacity(Long droneId);
}
