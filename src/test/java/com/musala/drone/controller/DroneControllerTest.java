package com.musala.drone.controller;

import com.musala.drone.services.DroneService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@Log4j2
@WebMvcTest
@AutoConfigureMockMvc
public class DroneControllerTest {
    @MockBean
    private DroneService droneService;
    @Autowired
    DroneAppController droneAppController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Correct Drone params")
    public void whenPostRequestToDroneAndValidDrone_thenCorrectResponse() throws Exception {
        String drone = "{\"serialNumber\": \"serial\", \"model\" : \"Lightweight\", \"weight\" : 400.0,\"batteryCapacity\" : 100,\"state\" : \"LOADING\"}";
        var res =mockMvc.perform(MockMvcRequestBuilders.post("/drones/register")
                        .content(drone)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        log.info("Res: {}",res.getResponse().getContentAsString());
    }
    @Test
    @DisplayName("InCorrect Drone params")
    public void whenPostRequestToDroneAndInValidDrone_thenBadReqResponse() throws Exception {
        String drone = "{\"serialNumber\": \"serial\", \"model\" : \"Lightweigh\", \"weight\" : 400.0,\"batteryCapacity\" : 100,\"state\" : \"LOADING\"}";
        var res =mockMvc.perform(MockMvcRequestBuilders.post("/drones/register")
                        .content(drone)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        log.info("Res: {}",res.getResponse().getContentAsString());
    }
}
