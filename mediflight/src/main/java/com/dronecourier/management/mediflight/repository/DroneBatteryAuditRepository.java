package com.dronecourier.management.mediflight.repository;

import com.dronecourier.management.mediflight.model.DroneBatteryAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAudit, UUID> {

}