package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.dto.MedicationWithoutImageDto;
import com.dronecourier.management.mediflight.dto.MedicineDeliveryDroneDto;
import com.dronecourier.management.mediflight.mapper.DroneMapper;
import com.dronecourier.management.mediflight.mapper.MedicationMapper;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.service.DroneServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drone")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DroneController {
    DroneServiceImpl droneServiceImpl;
    DroneMapper droneMapper;
    MedicationMapper medicationMapper;

    @PostMapping("/register")
    public void registerDrone(@Valid @RequestBody DroneDto droneDto) {
        if (droneDto.getId() == null)
            droneDto.setId(String.valueOf(UUID.randomUUID()));
        Drone drone = droneMapper.droneDtoToDrone(droneDto);
        droneServiceImpl.registerDrone(drone);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDto>> getAvailableDrones() {
        return ResponseEntity.ok(droneServiceImpl.getAvailableDrones()
                .stream()
                .map(droneMapper::droneToDroneDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/battery/{droneId}")
    public ResponseEntity<Integer> getDroneBatteryLevel(@Valid @PathVariable String droneId) {
        return ResponseEntity.ok(droneServiceImpl.getDroneBatteryLevel(droneId));
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<MedicineDeliveryDroneDto> getDroneWithCargo(@Valid @PathVariable String droneId) {
        Drone drone = droneServiceImpl.getRequestedDroneWithMedications(droneId);
        List<MedicationWithoutImageDto> dtoList = drone.getMedications()
                .stream()
                .map(medicationMapper::medicationToMedicationWithoutImageDto)
                .collect(Collectors.toList());
        MedicineDeliveryDroneDto medicineDeliveryDroneDto = new MedicineDeliveryDroneDto(droneMapper.droneToDroneDto(drone),
                dtoList);
        return ResponseEntity.ok(medicineDeliveryDroneDto);

    }
}