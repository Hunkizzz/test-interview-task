package com.dronecourier.management.mediflight.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("image")
public class ImageDto {
    @JsonProperty("uuid")
    String uuid;
    @JsonProperty("imageNumber")
    String imageNumber;
}
