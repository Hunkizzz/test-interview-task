package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.dto.MedicationWithoutImageDto;
import com.dronecourier.management.mediflight.dto.MedicineDeliveryDroneDto;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.mapper.DroneMapper;
import com.dronecourier.management.mediflight.mapper.MedicationMapper;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.repository.DroneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    DroneRepository droneRepository;
    DroneMapper droneMapper;
    MedicationMapper medicationMapper;

    @Override
    public void registerDrone(DroneDto droneDto) {
        if (droneDto.getId() == null)
            droneDto.setId(String.valueOf(UUID.randomUUID()));
        Drone drone = droneMapper.droneDtoToDrone(droneDto);
        droneRepository.save(drone);
    }

    @Override
    public Drone getRequestedDrone(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException("Drone with this id was not found: " + droneId));
    }

    @Override
    public MedicineDeliveryDroneDto getRequestedDroneWithMedications(String droneId) {
        Drone drone = droneRepository.findDroneById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException("Drone with this id was not found: " + droneId));
        List<MedicationWithoutImageDto> dtoList = drone.getMedications()
                .stream()
                .map(medicationMapper::medicationToMedicationWithoutImageDto)
                .collect(Collectors.toList());
        return new MedicineDeliveryDroneDto(droneMapper.droneToDroneDto(drone),
                dtoList);

    }

    @Override
    public void loadDrone(Drone drone) {
        drone.setState(DroneState.LOADING);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - 1);
        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);
    }

    @Override
    public Set<DroneDto> getAvailableDrones() {

        List<Drone> drones = droneRepository.findByState(DroneState.IDLE);
        return drones.stream().map(droneMapper::droneToDroneDto).collect(Collectors.toSet());
    }

    @Override
    public int getDroneBatteryLevel(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Drone with this id was not found: %s", droneId)))
                .getBatteryCapacity();
    }

    @Override
    public void save(Drone drone) {
        droneRepository.save(drone);
    }

}