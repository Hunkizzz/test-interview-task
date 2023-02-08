package com.dronecourier.management.mediflight.mapper;

import com.dronecourier.management.mediflight.dto.DroneDto;
import com.dronecourier.management.mediflight.model.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    @Mapping(source = "id", target = "id")
    DroneDto DroneToDroneDto(Drone drone);

    @Mapping(source = "id", target = "id")
    Drone DroneDtoToDrone(DroneDto droneDto);
}