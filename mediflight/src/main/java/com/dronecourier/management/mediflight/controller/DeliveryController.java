package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DeliveryDto;
import com.dronecourier.management.mediflight.service.DeliveryServiceImpl;
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
    DeliveryServiceImpl deliveryServiceImpl;

    @PostMapping("/load")
    public ResponseEntity<String> loadDrone(@RequestBody DeliveryDto deliveryDto) {
        deliveryServiceImpl.loadDrone(deliveryDto);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/deliver/{droneId}")
    public ResponseEntity<String> deliverDrone(@PathVariable String droneId) {
        deliveryServiceImpl.deliverDrone(droneId);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/return/{droneId}")
    public ResponseEntity<String> returnDrone(@PathVariable String droneId) {
        deliveryServiceImpl.returnDrone(droneId);
        return ResponseEntity.ok("Ok");
    }
}
