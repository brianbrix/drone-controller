package com.musala.drone.services;

import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface DroneService {
    Drone registerDrone(Drone drone);
    Drone loadMedicationItems(Long droneId, Set<Medication> medicationSet) throws Exception;
    Set<Medication> checkLoadedMedications(Long droneId);
    List<Drone> checkAvailableDrones();
    Double checkBatterCapacity(Long droneId);
}
