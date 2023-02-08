package com.dronecourier.management.mediflight.repository;

import com.dronecourier.management.mediflight.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {
    List<Medication> findAllByDroneId(UUID uuid);

    List<Medication> findAll();
}