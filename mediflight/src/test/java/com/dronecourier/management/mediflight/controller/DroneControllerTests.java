package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.dto.MedicationWithoutImageDto;
import com.dronecourier.management.mediflight.enums.DroneModel;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.mapper.DroneMapper;
import com.dronecourier.management.mediflight.mapper.MedicationMapper;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.model.Medication;
import com.dronecourier.management.mediflight.service.DroneServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DroneController.class)
class DroneControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneServiceImpl droneServiceImpl;

    @MockBean
    private DroneMapper droneMapper;

    @MockBean
    private MedicationMapper medicationMapper;

    static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void registerDroneTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        Drone drone = Drone.builder()
                .id(UUID.randomUUID())
                .batteryCapacity(50)
                .weightLimit(500)
                .serialNumber("sagasdg")
                .state(DroneState.IDLE)
                .model(DroneModel.HEAVYWEIGHT)
                .build();
        DroneDto droneDto = new DroneDto();
        droneDto.setId(String.valueOf(uuid));
        droneDto.setBatteryCapacity(drone.getBatteryCapacity());
        droneDto.setModel(drone.getModel());
        droneDto.setState(drone.getState());
        droneDto.setSerialNumber(drone.getSerialNumber());
        droneDto.setWeightLimit(drone.getWeightLimit());
        when(droneMapper.droneDtoToDrone(droneDto)).thenReturn(drone);

        mockMvc.perform(post("/drone/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(droneDto)))
                .andExpect(status().isOk());

        verify(droneServiceImpl, times(1)).save(drone);
    }

    @Test
    void getAvailableDronesTest() throws Exception {
        List<Drone> dronesList = new ArrayList<>();
        Drone drone1 = new Drone();
        drone1.setId(UUID.randomUUID());
        Drone drone2 = new Drone();
        drone1.setId(UUID.randomUUID());
        Drone drone3 = new Drone();
        drone1.setId(UUID.randomUUID());
        dronesList.add(drone1);
        dronesList.add(drone2);
        dronesList.add(drone3);

        when(droneServiceImpl.getAvailableDrones()).thenReturn(dronesList);
        when(droneMapper.droneToDroneDto(any())).thenReturn(new DroneDto());

        mockMvc.perform(get("/drone/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(droneServiceImpl, times(1)).getAvailableDrones();
    }

    @Test
    void getDroneBatteryLevelTest() throws Exception {
        String droneId = "1";
        int batteryLevel = 100;
        when(droneServiceImpl.getDroneBatteryLevel(droneId)).thenReturn(batteryLevel);

        mockMvc.perform(get("/drone/battery/" + droneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(batteryLevel));

        verify(droneServiceImpl, times(1)).getDroneBatteryLevel(droneId);
    }

    @Test
    void getDroneWithCargoTest() throws Exception {
        String droneId = "1";
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        Drone drone = new Drone();
        drone.setId(UUID.randomUUID());
        drone.setMedications(medications);
        when(droneServiceImpl.getRequestedDroneWithMedications(droneId)).thenReturn(drone);
        when(medicationMapper.medicationToMedicationWithoutImageDto(any())).thenReturn(new MedicationWithoutImageDto());

        mockMvc.perform(get("/drone/" + droneId + "/medications")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(droneServiceImpl, times(1)).getRequestedDroneWithMedications(droneId);
    }
}