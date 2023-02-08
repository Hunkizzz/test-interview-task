package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.dto.MedicineDeliveryDroneDto;
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

import java.util.Set;

@RestController
@RequestMapping("/drone")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DroneController {
    DroneServiceImpl droneServiceImpl;

    @PostMapping("/register")
    public void registerDrone(@RequestBody DroneDto droneDto) {
        droneServiceImpl.registerDrone(droneDto);
    }

    @GetMapping("/available")
    public ResponseEntity<Set<DroneDto>> getAvailableDrones() {
        return ResponseEntity.ok(droneServiceImpl.getAvailableDrones());
    }

    @GetMapping("/battery/{droneId}")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable String droneId) {
        return ResponseEntity.ok(droneServiceImpl.getDroneBatteryLevel(droneId));
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<MedicineDeliveryDroneDto> getDroneWithCargo(@PathVariable String droneId) {
        return ResponseEntity.ok(droneServiceImpl.getRequestedDroneWithMedications(droneId));

    }
}