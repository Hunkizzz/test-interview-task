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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    DroneService droneService;
    MedicationService medicationService;
    Validator validator;

    @Override
    public void loadDrone(DeliveryDto deliveryDto) {
        Drone drone = droneService.getRequestedDrone(deliveryDto.getDroneId());
        if (drone == null) {
            throw new EntityNotFoundException("Drone with this id was not found: " + deliveryDto.getDroneId());
        }
        if (drone.getState() != DroneState.IDLE) {
            throw new IllegalArgumentException("The drone is not in a state to be loaded");
        }
        if (drone.getBatteryCapacity() < 25) {
            throw new EntityNotFoundException("Low charge level - cannot load medications.\n " +
                    "The drone must be recharged before loading medications. current charge level is " + drone.getBatteryCapacity());
        }
        List<Medication> medicationList = medicationService.getMedicationList(deliveryDto.getMedicationIds());
        validateMedicationList(medicationList, deliveryDto);
        if (drone.getWeightLimit() >= medicationList.stream().mapToLong(Medication::getWeight).sum()) {
            droneService.loadDrone(drone);
            medicationList.forEach(medication -> medication.setDroneId(drone.getId()));
            medicationService.setSupplierForMedication(medicationList);
        } else {
            throw new IllegalArgumentException("The drone cannot carry the medications");
        }
    }

    private void validateMedicationList(List<Medication> medicationList, DeliveryDto deliveryDto) {
        Set<ConstraintViolation<Medication>> violations = new HashSet<>();
        medicationList.stream().map((Function<Medication, Set<ConstraintViolation<Medication>>>) validator::validate).forEach(violations::addAll);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Medication> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
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
        String busyMedications = medicationList.stream()
                .filter(medication -> medication.getDroneId() != null)
                .map(Medication::getDroneId)
                .map(String::valueOf).collect(Collectors.joining(", "));
        if (!busyMedications.isEmpty()) {
            throw new IllegalArgumentException("These medications are busy: " + busyMedications);
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
