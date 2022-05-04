package com.musala.drone.services.impl;

import com.google.gson.Gson;
import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.entity.MedicationEntity;
import com.musala.drone.db.repos.DroneRepository;
import com.musala.drone.exceptions.BatteryException;
import com.musala.drone.exceptions.NotFoundException;
import com.musala.drone.exceptions.WeightException;
import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.services.DroneService;
import com.musala.drone.services.RepoService;
import com.musala.drone.utils.FileSaver;
import com.musala.drone.utils.GenericMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final RepoService repoService;
    private final DroneRepository droneRepository;
    GenericMapper<Drone, DroneEntity> genericMapper= new GenericMapper<>();
    GenericMapper<Medication, MedicationEntity> genericMapper2= new GenericMapper<>();
    @Override
    public Drone registerDrone(Drone drone) {

        var droneEntity= genericMapper.mapForward(drone,DroneEntity.class);
        var addedDrone = repoService.addDrone(droneEntity);
        return genericMapper.mapReverse(addedDrone,Drone.class);
    }

    @Override
    public Drone loadMedicationItems(Long droneId, Medication medication, MultipartFile file) throws Exception {
        log.info("In service");
        var drone= repoService.findById(droneId);
        log.info(new Gson().toJson(medication));

        if (drone.isPresent())
        {


            var filePath = FileSaver.save(file, medication.getName());
            var medicationEntity= genericMapper2.mapForward(medication,MedicationEntity.class);
            medicationEntity.setImage(filePath);

            var d = drone.get();
            var weight= d.getWeight();
            weight+=medication.getWeight();
            log.info("Weight: {}",weight);
            if (weight>500)
            {
                throw new WeightException("The drone will exit weight limit. Please Reduce the load!!!");
            }
            d.setWeight(weight);
            var batteryCapacity= d.getBatteryCapacity();
            if (batteryCapacity<25)
            {
                throw new BatteryException("Battery is below 25%!!!");
            }
            //Assume that each medication will consume 10 % of the battery
            batteryCapacity-=10;
            d.setBatteryCapacity(batteryCapacity);
            var medicationEntities= d.getItems();
            medicationEntity.setDrone(d);
            medicationEntities.add(medicationEntity);

            d.setItems(medicationEntities);
            var saveDrone = droneRepository.save(d);

            var res = genericMapper.mapReverse(saveDrone,Drone.class);
            //We have to get the list due to type erasure of ModelMapper at runtime
            var ls = medicationEntities.stream().map(med ->genericMapper2.mapReverse(med,Medication.class)).collect(Collectors.toSet());
            res.setItems(ls);
            return res;
        }
        throw  new NotFoundException("Drone not found.");
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
