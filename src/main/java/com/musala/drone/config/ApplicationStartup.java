package com.musala.drone.config;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.entity.MedicationEntity;
import com.musala.drone.db.repos.DroneRepository;
import com.musala.drone.enums.ModelEnum;
import com.musala.drone.enums.StateEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to initialize DB data
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private DroneRepository droneRepository;

    /**
     * This event is executed as soon as
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        List<DroneEntity> droneEntities = new ArrayList<>();
        for (int i=0;i<=9;i++)
        {
        var d = DroneEntity.builder()
                .batteryCapacity(100.0)
                .weightLimit(100.0)
                .model(ModelEnum.Cruiserweight)
                .serialNumber(RandomStringUtils.randomAlphabetic(40))
                .state(StateEnum.IDLE)
                .build();
        List<MedicationEntity> medicationEntities= new ArrayList<>();
        for (int j=0;j<5;j++)
        {
           medicationEntities.add(
                   MedicationEntity.builder()
                           .code(RandomStringUtils.randomAlphabetic(10))
                           .image(RandomStringUtils.randomAlphabetic(20))
                           .name(RandomStringUtils.randomAlphabetic(5))
                           .weight(50.0)
                           .drone(d)
                           .build()
           );
        }
        d.setItems(medicationEntities);
        droneEntities.add(d);
        }
        droneRepository.saveAll(droneEntities);

    }
}