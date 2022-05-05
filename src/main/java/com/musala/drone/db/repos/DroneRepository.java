package com.musala.drone.db.repos;

import com.musala.drone.db.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    @Override
    List<DroneEntity> findAll();

    List<DroneEntity> findAllByAvailable(Boolean available);

}
