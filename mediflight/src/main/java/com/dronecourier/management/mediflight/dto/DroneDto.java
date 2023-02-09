package com.dronecourier.management.mediflight.dto;

import com.dronecourier.management.mediflight.enums.DroneModel;
import com.dronecourier.management.mediflight.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonRootName("drone")
public class DroneDto {
    @JsonProperty("id")
    String id;
    @JsonProperty("serialNumber")
    @NotNull
    @Pattern(regexp = "[\\w\\s]{1,100}", message = "Serial number must be provided and must be shorter than 100 characters")
    String serialNumber;
    @JsonProperty("model")
    DroneModel model;
    @JsonProperty("weightLimit")
    @Max(500)
    long weightLimit;
    @JsonProperty("batteryCapacity")
    @Max(100)
    @Min(0)
    int batteryCapacity;
    @JsonProperty("state")
    DroneState state;
}