package com.musala.drone.jobs;

import com.musala.drone.models.Drone;
import com.musala.drone.services.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@EnableScheduling
@Log4j2
public class BatteryCheckJob {
    @Autowired
    private DroneService droneService;
    FileWriter fileWriter;
    Path p;
    BatteryCheckJob() throws IOException {
        Path source = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath());
        Path newFolder = Paths.get(source.toAbsolutePath() + "/logs/");
        Files.createDirectories(newFolder);
        p=Paths.get(newFolder.toAbsolutePath()+"/battery.log");
        log.info("EXISTS: {}", Files.exists(p, LinkOption.NOFOLLOW_LINKS));
        if (!Files.exists(p, LinkOption.NOFOLLOW_LINKS))
            p = Files.createFile(Paths.get(newFolder.toAbsolutePath()+"/battery.log"));

    }
    @Scheduled(cron = "0/20 * * * * ?")
    public void checkBatteryCapacity() throws IOException {
        var drones= droneService.getAllDrones();
        fileWriter= new FileWriter(p.toAbsolutePath().toFile(),true);
        log.info(p.toAbsolutePath().toString());
        for (Drone drone: drones)
        {
            log.info("Date: "+ LocalDateTime.now() +"  Drone: "+drone.getId()+"  Serial: "+drone.getSerialNumber()+"  BATTERY: "+drone.getBatteryCapacity());
            fileWriter.write("Date: "+ LocalDateTime.now() +"  Drone: "+drone.getId()+"  Serial: "+drone.getSerialNumber()+"  BATTERY: "+drone.getBatteryCapacity()+"\n");

        }
        fileWriter.close();

    }





}
