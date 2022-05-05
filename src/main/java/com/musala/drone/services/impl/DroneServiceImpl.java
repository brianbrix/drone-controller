package com.musala.drone.services.impl;

import com.google.gson.Gson;
import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.entity.MedicationEntity;
import com.musala.drone.db.repos.DroneRepository;
import com.musala.drone.enums.StateEnum;
import com.musala.drone.exceptions.BatteryException;
import com.musala.drone.exceptions.NotFoundException;
import com.musala.drone.exceptions.WeightException;
import com.musala.drone.models.Drone;
import com.musala.drone.models.Medication;
import com.musala.drone.models.resp.BatteryResp;
import com.musala.drone.services.DroneService;
import com.musala.drone.services.RepoService;
import com.musala.drone.utils.FileSaver;
import com.musala.drone.utils.GenericMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
        drone.setState("IDLE");
        var droneEntity= genericMapper.mapForward(drone,DroneEntity.class);
        var addedDrone = repoService.addDrone(droneEntity);
        log.info("ADDED: {}", new Gson().toJson(addedDrone));
        return genericMapper.mapReverse(addedDrone,Drone.class);
    }

    @Override
    public Drone loadMedicationItems(Long droneId, Medication medication, MultipartFile file) throws Exception {
        log.info("In service");
        var drone= repoService.findById(droneId);
        log.info(new Gson().toJson(medication));
        var filePath = FileSaver.save(file, file.getOriginalFilename());
        var medicationEntity= genericMapper2.mapForward(medication,MedicationEntity.class);
        medicationEntity.setImage(filePath);

        var droneEntity = droneRepository.save(addItems(droneId, medication));
            return genericMapper.mapReverse(droneEntity,Drone.class);

    }

    private DroneEntity addItems(Long droneId, Medication medication)
    {
        var drone= repoService.findById(droneId);

        if (drone.isPresent()) {

            var medicationEntity = genericMapper2.mapForward(medication, MedicationEntity.class);
            var droneEntity = drone.get();
            if (droneEntity.getState().equals(StateEnum.IDLE) || droneEntity.getState().equals(StateEnum.LOADING)) {
                var weight = droneEntity.getWeight();
                weight += medication.getWeight();
                log.info("Weight: {}", weight);
                if (weight > 500) {
                    droneEntity.setState(StateEnum.LOADED);
                    throw new WeightException("The drone will exceed weight limit. Please Reduce the load!!!. Drone ID:" + droneEntity.getId());
                }
                droneEntity.setState(StateEnum.LOADING);
                droneEntity.setWeight(weight);
                var batteryCapacity = droneEntity.getBatteryCapacity();
                if (batteryCapacity < 25) {
                    droneEntity.setState(StateEnum.LOADED);
                    throw new BatteryException("Battery is below 25%!!!");
                }
                droneEntity.setState(StateEnum.LOADING);
                //Assume that each medication will consume 10 % of the battery
                batteryCapacity -= 10;
                droneEntity.setBatteryCapacity(batteryCapacity);
                var medicationEntities = droneEntity.getItems();
                medicationEntity.setDrone(droneEntity);
                medicationEntities.add(medicationEntity);

                droneEntity.setItems(medicationEntities);

                return droneEntity;
            }
        }
            throw  new NotFoundException("Drone not found.");
    }

    @Override
    public Drone loadMedicationItemsList(Long droneId, List<Medication> medications) throws Exception {
        DroneEntity droneEntity= new DroneEntity();
        for (Medication medication: medications)
        {
             droneEntity = addItems(droneId,medication);
        }
        var ls = droneEntity.getItems().stream().map(med ->genericMapper2.mapReverse(med,Medication.class)).collect(Collectors.toSet());
        var res = genericMapper.mapReverse(droneRepository.save(droneEntity),Drone.class);
        res.setItems(ls);
        return res;
    }

    @Override
    public List<Medication> checkLoadedMedications(Long droneId) {
        var drone= repoService.findById(droneId);

        if (drone.isPresent())
        {
            var medicationEntities = drone.get().getItems();
            return  medicationEntities.stream().map(med ->genericMapper2.mapReverse(med,Medication.class)).collect(Collectors.toList());
        }
        throw  new NotFoundException("Drone not found.");
    }

    @Override
    public List<Drone> checkAvailableDrones() {
        return repoService.findAvailable().stream().map(droneEntity -> genericMapper.mapReverse(droneEntity, Drone.class)).toList();
    }

    @Override
    public List<Drone> getAllDrones() {
        return repoService.findAll().stream().map(droneEntity -> genericMapper.mapReverse(droneEntity, Drone.class)).toList();
    }

    @Override
    public BatteryResp checkBatteryCapacity(Long droneId) {
        var drone= repoService.findById(droneId);

        if (drone.isPresent()) {
        return BatteryResp.builder()
                .batteryCapacity(drone.get().getBatteryCapacity())
                .build();
        }
        else
            throw new NotFoundException("Drone not found.");
    }

    @Override
    public Drone editDrone(Long droneId,Drone drone) {
        var optionalDroneEntity= repoService.findById(droneId);
        if (optionalDroneEntity.isPresent()) {
             var droneEntity = optionalDroneEntity.get();
            droneEntity = droneRepository.save(genericMapper.mapForward(drone,DroneEntity.class));
            return genericMapper.mapReverse(droneEntity, Drone.class);

        }
        throw new NotFoundException("Drone not found");
    }


}
