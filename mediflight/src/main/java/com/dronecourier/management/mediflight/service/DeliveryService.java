package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.DeliveryDto;

public interface DeliveryService {
    void loadDrone(DeliveryDto deliveryDto);

    void deliverDrone(String droneId);

    void returnDrone(String droneId);
}
