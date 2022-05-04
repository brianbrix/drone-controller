package com.musala.drone.db.repos;

import com.musala.drone.db.entity.DroneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends CrudRepository<DroneEntity, Long> {
}
