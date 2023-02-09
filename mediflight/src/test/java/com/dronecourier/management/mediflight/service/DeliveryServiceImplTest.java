package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.DeliveryDto;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.model.Medication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {
    @Mock
    DroneService droneService;
    @Mock
    MedicationService medicationService;

    @InjectMocks
    DeliveryServiceImpl deliveryService;

    @Test
    void loadDrone_whenDroneNotFound_shouldThrowEntityNotFoundException() {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId("1");
        deliveryDto.setMedicationIds(Arrays.asList("1", "2"));
        when(droneService.getRequestedDrone("1")).thenReturn(null);
        assertThatThrownBy(() -> deliveryService.loadDrone(deliveryDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Drone with this id was not found: 1");
    }

    @Test
    void loadDrone_whenDroneChargeLow_shouldThrowEntityNotFoundException() {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId("1");
        deliveryDto.setMedicationIds(Arrays.asList("1", "2"));
        Drone drone = new Drone();
        drone.setId(UUID.fromString("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a"));
        drone.setState(DroneState.IDLE);
        drone.setBatteryCapacity(10);
        when(droneService.getRequestedDrone("1")).thenReturn(drone);
        assertThatThrownBy(() -> deliveryService.loadDrone(deliveryDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Low charge level - cannot load medications.\n The drone must be recharged before loading medications. current charge level is 10");
    }

    @Test
    void loadDrone_whenMedicationNotFound_shouldThrowEntityNotFoundException() {
        String uuid = String.valueOf(UUID.randomUUID());
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a");
        deliveryDto.setMedicationIds(Arrays.asList(uuid, "70f7b8c9-c5e5-4d5b-8b30-e5c7f5d0cf8f"));
        Drone drone = new Drone();
        drone.setState(DroneState.IDLE);
        drone.setId(UUID.fromString("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a"));
        drone.setBatteryCapacity(50);
        when(droneService.getRequestedDrone("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a")).thenReturn(drone);
        Medication medication = new Medication();
        medication.setId(UUID.fromString(uuid));
        when(medicationService.getMedicationList(deliveryDto.getMedicationIds())).thenReturn(Collections.singletonList(medication));
        assertThatThrownBy(() -> deliveryService.loadDrone(deliveryDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("This ids of medications were not found : 70f7b8c9-c5e5-4d5b-8b30-e5c7f5d0cf8f");
    }

    @Test
    void loadDrone_whenDroneWeightLimitExceeded_shouldThrowIllegalArgumentException() {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDroneId("1");
        deliveryDto.setMedicationIds(Arrays.asList("1", "2"));
        Drone drone = new Drone();
        drone.setId(UUID.fromString("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a"));
        drone.setBatteryCapacity(50);
        drone.setWeightLimit(100);
        drone.setState(DroneState.IDLE);
        when(droneService.getRequestedDrone("1")).thenReturn(drone);
        List<Medication> medications = Arrays.asList(new Medication(), new Medication());
        medications.forEach(medication -> medication.setWeight(200));
        when(medicationService.getMedicationList(deliveryDto.getMedicationIds())).thenReturn(medications);
        assertThatThrownBy(() -> deliveryService.loadDrone(deliveryDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The drone cannot carry the medications");
    }

    @Test
    void deliverDrone_whenDroneNotLoaded_shouldThrowIllegalArgumentException() {
        Drone drone = new Drone();
        drone.setId(UUID.fromString("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a"));
        drone.setState(DroneState.IDLE);
        when(droneService.getRequestedDrone("1")).thenReturn(drone);
        assertThatThrownBy(() -> deliveryService.deliverDrone("1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The drone is not in a state to be delivered");
    }

    @Test
    void returnDrone_whenDroneNotDelivered_shouldThrowIllegalArgumentException() {
        Drone drone = new Drone();
        drone.setId(UUID.fromString("dafb1a60-c953-4baa-b8e8-908b7b8f1b0a"));
        drone.setState(DroneState.IDLE);
        when(droneService.getRequestedDrone("1")).thenReturn(drone);
        assertThatThrownBy(() -> deliveryService.returnDrone("1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The drone is not in a state to return");
    }
}