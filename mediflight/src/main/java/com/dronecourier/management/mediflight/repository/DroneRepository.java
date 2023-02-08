package com.dronecourier.management.mediflight.repository;

import com.dronecourier.management.mediflight.enums.DroneState;
import com.dronecourier.management.mediflight.model.Drone;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {

    List<Drone> findByState(DroneState state);

    @EntityGraph(value = "medication_entity_graph")
    Optional<Drone> findDroneById(UUID uuid);

    Optional<Drone> findById(UUID uuid);


}