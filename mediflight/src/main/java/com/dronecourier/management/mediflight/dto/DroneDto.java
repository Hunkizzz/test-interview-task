package com.dronecourier.management.mediflight.dto;

import com.dronecourier.management.mediflight.enums.DroneModel;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonRootName("drone")
public class DroneDto {
    @JsonProperty("id")
    String id;
    @JsonProperty("serialNumber")
    String serialNumber;
    @JsonProperty("model")
    DroneModel model;
    @JsonProperty("weightLimit")
    long weightLimit;
    @JsonProperty("batteryCapacity")
    int batteryCapacity;
    @JsonProperty("state")
    DroneState state;
}