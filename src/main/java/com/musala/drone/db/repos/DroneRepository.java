package com.musala.drone.db.repos;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    @Override
    List<DroneEntity> findAll();

    List<DroneEntity> findAllByState(StateEnum stateEnum);

}
