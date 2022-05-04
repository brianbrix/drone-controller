package com.musala.drone.services.impl;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.repos.DroneRepository;
import com.musala.drone.services.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RepoServiceImpl implements RepoService {
    private final DroneRepository droneRepository;
    @Override
    public DroneEntity addDrone(DroneEntity droneEntity) {
        return droneRepository.save(droneEntity);
    }

    @Override
    public Optional<DroneEntity> findById(Long id) {
        return droneRepository.findById(id);
    }
}
