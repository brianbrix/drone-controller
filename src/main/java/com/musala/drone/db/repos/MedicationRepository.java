package com.musala.drone.db.repos;

import com.musala.drone.db.entity.MedicationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<MedicationEntity, Long> {
}
