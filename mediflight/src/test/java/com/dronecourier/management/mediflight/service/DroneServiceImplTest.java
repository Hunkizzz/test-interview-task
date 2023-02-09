package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.enums.DroneModel;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.repository.DroneRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DroneServiceImplTest {
    @Mock
    DroneRepository droneRepository;

    DroneServiceImpl droneService;

    UUID uuid = UUID.randomUUID();
    Drone drone;

    @BeforeAll
    void globalSetUp() {
        drone = Drone.builder()
                .id(uuid)
                .batteryCapacity(50)
                .weightLimit(500)
                .serialNumber("sagasdg")
                .state(DroneState.IDLE)
                .model(DroneModel.HEAVYWEIGHT)
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        droneService = new DroneServiceImpl(droneRepository);
    }

    @Test
    void shouldSaveDrone_WhenRegisterDrone() {
        droneService.registerDrone(drone);
        verify(droneRepository, times(1)).save(any(Drone.class));
    }

    @Test
    void shouldThrowException_WhenDroneNotFound_WhenGetRequestedDrone() {
        String droneId = UUID.randomUUID().toString();
        when(droneRepository.findById(any(UUID.class))).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> droneService.getRequestedDrone(droneId));
    }

    @Test
    void registerDrone() {
        Drone drone = new Drone();
        droneService.registerDrone(drone);
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    void getRequestedDrone() {
        UUID testUUID = UUID.randomUUID();
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));
        droneService.getRequestedDrone(testUUID.toString());
        verify(droneRepository, times(1)).findById(testUUID);
    }

    @Test
    void getRequestedDroneWithMedications() {
        UUID testUUID = UUID.randomUUID();
        when(droneRepository.findDroneById(any(UUID.class))).thenReturn(Optional.of(drone));
        droneService.getRequestedDroneWithMedications(testUUID.toString());
        verify(droneRepository, times(1)).findDroneById(testUUID);
    }

    @Test
    void loadDrone() {
        Drone drone = new Drone();
        droneService.loadDrone(drone);
        assertEquals(DroneState.LOADED, drone.getState());
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    void getAvailableDrones() {
        List<Drone> mockList = new ArrayList<>();
        when(droneRepository.findByState(DroneState.IDLE)).thenReturn(mockList);
        List<Drone> returnedList = droneService.getAvailableDrones();
        assertEquals(mockList, returnedList);
        verify(droneRepository, times(1)).findByState(DroneState.IDLE);
    }

    @Test
    void getDroneBatteryLevel() {
        UUID testUUID = UUID.randomUUID();
        int batteryLevel = 10;
        Drone drone = new Drone();
        drone.setBatteryCapacity(batteryLevel);
        when(droneRepository.findById(testUUID)).thenReturn(java.util.Optional.of(drone));
        assertEquals(batteryLevel, droneService.getDroneBatteryLevel(testUUID.toString()));
        verify(droneRepository, times(1)).findById(testUUID);
    }

    @Test
    void save() {
        Drone drone = new Drone();
        droneService.save(drone);
        verify(droneRepository, times(1)).save(drone);
    }
}
