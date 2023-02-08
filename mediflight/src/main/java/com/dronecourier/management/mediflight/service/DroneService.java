package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.dto.MedicineDeliveryDroneDto;
import com.dronecourier.management.mediflight.model.Drone;

import java.util.Set;

public interface DroneService {
    void registerDrone(DroneDto droneDto);

    Drone getRequestedDrone(String droneId);

    MedicineDeliveryDroneDto getRequestedDroneWithMedications(String droneId);

    void loadDrone(Drone drone);

    Set<DroneDto> getAvailableDrones();

    int getDroneBatteryLevel(String droneId);

    void save(Drone drone);
}
