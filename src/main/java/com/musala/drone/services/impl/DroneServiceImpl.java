package com.musala.drone.services.impl;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.entity.MedicationEntity;
import com.musala.drone.exceptions.NotFoundException;
import com.musala.drone.exceptions.NullObjectException;
import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.services.DroneService;
import com.musala.drone.services.RepoService;
import com.musala.drone.utils.FileSaver;
import com.musala.drone.utils.GenericMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final RepoService repoService;
    GenericMapper<Drone, DroneEntity> genericMapper= new GenericMapper<>();
    GenericMapper<Medication, MedicationEntity> genericMapper2= new GenericMapper<>();
    @Override
    public Drone registerDrone(Drone drone) {

        var droneEntity= genericMapper.mapForward(drone,DroneEntity.class);
        var addedDrone = repoService.addDrone(droneEntity);
        return genericMapper.mapReverse(addedDrone,Drone.class);
    }

    @Override
    public Drone loadMedicationItems(Long droneId, Set<Medication> medicationSet) throws Exception {
        log.info("In service");
        var drone= repoService.findById(droneId);
        Set<MedicationEntity> medicationEntities = new HashSet<>();
        if (medicationSet.isEmpty())
        {
            throw new NullObjectException("Medication items list is empty.");
        }
        if (drone.isPresent())
        {
            for (Medication medication: medicationSet)
            {
                var filePath = FileSaver.save(medication.getImage(), medication.getName());
                var medicationEntity= genericMapper2.mapForward(medication,MedicationEntity.class);
                medicationEntity.setImage(filePath);
                medicationEntities.add(medicationEntity);
            }
            var d = drone.get();
            d.setMedications(medicationEntities);
            return genericMapper.mapReverse(repoService.addDrone(d),Drone.class);
        }
        throw  new NotFoundException("Drone not found");
    }

    @Override
    public Set<Medication> checkLoadedMedications(Long droneId) {
        return null;
    }

    @Override
    public List<Drone> checkAvailableDrones() {
        return null;
    }

    @Override
    public Double checkBatterCapacity(Long droneId) {
        return null;
    }


}
