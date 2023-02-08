package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.DeliveryDto;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.model.Drone;
import com.dronecourier.management.mediflight.model.Medication;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    DroneService droneService;
    MedicationService medicationService;

    @Override
    public void loadDrone(DeliveryDto deliveryDto) {
        Drone drone = droneService.getRequestedDrone(deliveryDto.getDroneId());
        if (drone == null) {
            throw new EntityNotFoundException("Drone with this id was not found: " + deliveryDto.getDroneId());
        }
        if (drone.getBatteryCapacity() < 25) {
            throw new EntityNotFoundException("Low charge level - cannot load medications.\n " +
                    "The drone must be recharged before loading medications. current charge level is " + drone.getBatteryCapacity());
        }
        List<Medication> medicationList = medicationService.getMedicationList(deliveryDto.getMedicationIds());
        if (deliveryDto.getMedicationIds().size() != medicationList.size()) {

            String absentIds = deliveryDto.getMedicationIds()
                    .stream()
                    .filter(medicationId -> !medicationList.stream()
                            .map(Medication::getId)
                            .collect(Collectors.toList())
                            .contains(UUID.fromString(medicationId)))
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new EntityNotFoundException("This ids of medications were not found : " + absentIds);
        }
        if (drone.getWeightLimit() >= medicationList.stream().mapToLong(Medication::getWeight).sum()) {
            droneService.loadDrone(drone);
            medicationList.forEach(medication -> medication.setDroneId(drone.getId()));
            medicationService.setSupplierForMedication(medicationList);
        } else {
            throw new IllegalArgumentException("The drone cannot carry the medications");
        }
    }

    @Override
    public void deliverDrone(String droneId) {
        Drone drone = droneService.getRequestedDrone(droneId);
        if (drone.getState() == DroneState.LOADED) {
            drone.setState(DroneState.DELIVERING);
            drone.setBatteryCapacity(drone.getBatteryCapacity() - 1);
            drone.setState(DroneState.DELIVERED);
            droneService.save(drone);
            List<Medication> medications = medicationService.getTransferedMedications(droneId);
            medications.forEach(medication -> medication.setDroneId(null));
            medicationService.save(medications);
        } else {
            throw new IllegalArgumentException("The drone is not in a state to be delivered");
        }
    }

    @Override
    public void returnDrone(String droneId) {
        Drone drone = droneService.getRequestedDrone(droneId);
        if (drone.getState() == DroneState.DELIVERED) {
            drone.setState(DroneState.RETURNING);
            drone.setBatteryCapacity(drone.getBatteryCapacity() + 1);
            drone.setState(DroneState.IDLE);
            droneService.save(drone);
        } else {
            throw new IllegalArgumentException("The drone is not in a state to return");
        }
    }

}
