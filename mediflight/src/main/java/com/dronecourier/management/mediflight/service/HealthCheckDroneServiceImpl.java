package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.model.DroneBatteryAudit;
import com.dronecourier.management.mediflight.repository.DroneBatteryAuditRepository;
import com.dronecourier.management.mediflight.repository.DroneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HealthCheckDroneServiceImpl implements HealthCheckDroneService {

    DroneRepository droneRepository;
    DroneBatteryAuditRepository droneBatteryAuditRepository;

    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional
    public void checkDronesBatteryLevel() {
        List<Drone> drones = droneRepository.findAll();
        List<DroneBatteryAudit> batteryAudits = new ArrayList<>();
        for (Drone drone : drones) {
            DroneBatteryAudit audit = new DroneBatteryAudit();
            audit.setTimeStamp(LocalDateTime.now());
            audit.setBatteryLevel(drone.getBatteryCapacity());
            audit.setDrone(drone);
            batteryAudits.add(audit);
        }
        droneBatteryAuditRepository.saveAll(batteryAudits);
    }
}
