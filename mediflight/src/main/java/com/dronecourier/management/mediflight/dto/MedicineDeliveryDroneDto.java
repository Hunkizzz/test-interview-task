package com.dronecourier.management.mediflight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonRootName("medicineDeliveryDrone")
public class MedicineDeliveryDroneDto {

    @JsonProperty("drone")
    DroneDto drone;

    @JsonProperty("medications")
    List<MedicationWithoutImageDto> medications;
}
