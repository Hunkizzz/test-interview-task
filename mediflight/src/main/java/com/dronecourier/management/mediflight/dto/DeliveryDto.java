package com.dronecourier.management.mediflight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "delivery")
public class DeliveryDto {
    @JsonProperty("droneId")
    String droneId;
    @JsonProperty("medicationIds")
    List<String> medicationIds;
}