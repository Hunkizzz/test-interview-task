package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DeliveryDto;
import com.dronecourier.management.mediflight.service.DeliveryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeliveryController {
    DeliveryService deliveryService;

    @PostMapping("/load")
    public ResponseEntity<String> loadDrone(@RequestBody DeliveryDto deliveryDto) {
        deliveryService.loadDrone(deliveryDto);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/deliver/{droneId}")
    public ResponseEntity<String> deliverDrone(@PathVariable String droneId) {
        deliveryService.deliverDrone(droneId);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/return/{droneId}")
    public ResponseEntity<String> returnDrone(@PathVariable String droneId) {
        deliveryService.returnDrone(droneId);
        return ResponseEntity.ok("Ok");
    }
}
