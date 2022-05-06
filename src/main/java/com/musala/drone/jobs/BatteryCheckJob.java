package com.musala.drone.jobs;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.db.repos.DroneRepository;
import com.musala.drone.enums.StateEnum;
import com.musala.drone.services.RepoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
@EnableScheduling
@Log4j2
public class BatteryCheckJob {

    @Autowired
    private RepoService repoService;
    @Autowired
    private DroneRepository droneRepository;
    FileWriter fileWriter;
    Path p;
    File file;
    BatteryCheckJob() {
        String home = System.getProperty("user.home");
        p = Paths.get(home+"/Documents/battery.log"); // For testing on local
//        p = Paths.get("/user/local/Documents/battery.log");
        log.info("File Exists: {}",Files.exists(p, LinkOption.NOFOLLOW_LINKS));
        if (!Files.exists(p, LinkOption.NOFOLLOW_LINKS))
//            file = new File("/user/local/Documents/battery.log");
            file = new File(home+"/Documents/battery.log");
        else
            file = p.toFile();
    }
    @Scheduled(cron = "0/5 * * * * ?")
    public void checkBatteryCapacity() throws IOException {
        var drones= repoService.findAll();
        fileWriter= new FileWriter(file,true);
        log.info(fileWriter);
        for (DroneEntity drone: drones)
        {
            log.info("Date: "+ LocalDateTime.now() +"  Drone: "+drone.getId()+"  Serial: "+drone.getSerialNumber()+"  BATTERY: "+drone.getBatteryCapacity());
            fileWriter.write("Date: "+ LocalDateTime.now() +"  Drone: "+drone.getId()+"  Serial: "+drone.getSerialNumber()+"  BATTERY: "+drone.getBatteryCapacity()+"\n");
            if (drone.getBatteryCapacity()<25)
            {
                drone.setState(StateEnum.LOADED);
            }
        }
        droneRepository.saveAll(drones);

        fileWriter.close();

    }





}
