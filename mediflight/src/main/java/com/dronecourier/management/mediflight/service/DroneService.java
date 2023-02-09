package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.model.Drone;

import java.util.List;

public interface DroneService {
    void registerDrone(Drone droneDto);

    Drone getRequestedDrone(String droneId);

    Drone getRequestedDroneWithMedications(String droneId);

    void loadDrone(Drone drone);

    List<Drone> getAvailableDrones();

    int getDroneBatteryLevel(String droneId);

    void save(Drone drone);
}
