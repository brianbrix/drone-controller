package com.musala.drone.services;

import com.musala.drone.db.entity.DroneEntity;

import java.util.List;
import java.util.Optional;


public interface RepoService {
    DroneEntity addDrone(DroneEntity droneEntity);
    Optional<DroneEntity> findById(Long id);
    List<DroneEntity> findAvailable();
    List<DroneEntity> findAll();


}
