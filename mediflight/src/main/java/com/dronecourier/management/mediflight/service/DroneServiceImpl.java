package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.repository.DroneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DroneServiceImpl implements DroneService {

    DroneRepository droneRepository;

    @Override
    @Transactional
    public void registerDrone(Drone drone) {
        droneRepository.save(drone);
    }

    @Override
    public Drone getRequestedDrone(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException("Drone with this id was not found: " + droneId));
    }

    @Override
    public Drone getRequestedDroneWithMedications(String droneId) {
        return droneRepository.findDroneById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException("Drone with this id was not found: " + droneId));
    }

    @Override
    @Transactional
    public void loadDrone(Drone drone) {
        drone.setState(DroneState.LOADING);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - 1);
        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findByState(DroneState.IDLE);
    }

    @Override
    public int getDroneBatteryLevel(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Drone with this id was not found: %s", droneId)))
                .getBatteryCapacity();
    }

    @Override
    @Transactional
    public void save(Drone drone) {
        droneRepository.save(drone);
    }

}