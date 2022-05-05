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
import com.musala.drone.utils.EntityMapper;
import com.musala.drone.utils.FileSaver;
import com.musala.drone.utils.GenericMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    /**
     * Adds single medication element with Multipart file to drone
     * @param droneId
     * @param medication
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Drone loadMedicationItem(Long droneId, Medication medication, MultipartFile file) throws Exception {
        log.info("In service");
        log.info(new Gson().toJson(medication));
        var filePath = FileSaver.save(file, file.getOriginalFilename());
        var medicationEntity= genericMapper2.mapForward(medication,MedicationEntity.class);
        medicationEntity.setImage(filePath);
        var drone= repoService.findById(droneId);

        if (drone.isPresent()) {
            var droneEntity = droneRepository.save(addItems(drone.get(), medication));
            return genericMapper.mapReverse(droneEntity, Drone.class);
        }
        throw new NotFoundException("Drone not found");


    }

    /**
     * This methods adds a medication to a given drone
     * @param drone
     * @param medication
     * @return Drone
     */
    private DroneEntity addItems(DroneEntity drone, Medication medication)
    {

        var medicationEntity = genericMapper2.mapForward(medication, MedicationEntity.class);
        if (drone.getState().equals(StateEnum.IDLE) || drone.getState().equals(StateEnum.LOADING)) {
                var weight = drone.getWeight();
                weight += medication.getWeight();
                log.info("Weight: {}", weight);
                if (weight > 500) {
                    drone.setState(StateEnum.LOADED);
                    throw new WeightException("The drone will exceed weight limit. Please Reduce the load!!!. Drone ID:" + drone.getId());
                }
                drone.setState(StateEnum.LOADING);
                drone.setWeight(weight);
                var batteryCapacity = drone.getBatteryCapacity();
                if (batteryCapacity < 25) {
                    drone.setState(StateEnum.LOADED);
                    throw new BatteryException("Battery is below 25%!!!");
                }
                drone.setState(StateEnum.LOADING);
                //Assume that each medication will consume 10 % of the battery
                batteryCapacity -= 10;
                drone.setBatteryCapacity(batteryCapacity);
                var medicationEntities = drone.getItems();
                medicationEntity.setDrone(drone);
                medicationEntities.add(medicationEntity);

                drone.setItems(medicationEntities);

                return drone;

        }
            throw  new NotFoundException("Drone not found.");
    }

    /**
     * Adds a list of medication items to Drone
     * @param droneId
     * @param medications
     * @return Drone
     * @throws Exception
     */
    @Override
    public Drone loadMedicationItemsList(Long droneId, List<Medication> medications) throws Exception {
        DroneEntity droneEntity= new DroneEntity();
        var drone= repoService.findById(droneId);

        if (drone.isPresent()) {
            for (Medication medication : medications) {
                droneEntity = addItems(drone.get(), medication);
            }
            var ls = droneEntity.getItems().stream().map(med -> genericMapper2.mapReverse(med, Medication.class)).collect(Collectors.toSet());
            var res = genericMapper.mapReverse(droneRepository.save(droneEntity), Drone.class);
            res.setItems(ls);
            return res;
        }
        throw new NotFoundException("Drone not found");
    }

    /**
     * Returns loaded medication items for a drone
     * @param droneId
     * @return List
     */
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

    /**
     * check battery capacity for a drone
     * @param droneId
     * @return BatteryResp
     */
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

    /**
     * Used to edit a drone's values
     * @param droneId
     * @param drone
     * @return updated Drone
     */
    @Override
    public Drone editDrone(Long droneId,Drone drone) {
        var optionalDroneEntity= repoService.findById(droneId);
        if (drone.getWeight()>500)
            throw  new WeightException("Weight too high.");
            if (optionalDroneEntity.isPresent()) {
                var droneEntity = optionalDroneEntity.get();
                var ls =  droneEntity.getItems().stream().map(medicationEntity -> genericMapper2.mapReverse(medicationEntity,Medication.class)).collect(Collectors.toSet());
                EntityMapper.INSTANCE.updateDroneFromDto(drone,droneEntity);
                droneEntity = droneRepository.save(droneEntity);
                var res= genericMapper.mapReverse(droneEntity, Drone.class);

                res.setItems(ls);
                return res;

            }


        throw new NotFoundException("Drone not found");
    }


}
