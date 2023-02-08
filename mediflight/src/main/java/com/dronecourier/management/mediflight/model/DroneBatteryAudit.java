package com.dronecourier.management.mediflight.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class DroneBatteryAudit {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "timestamp")
    private LocalDateTime timeStamp;

    @Column(name = "battery_level")
    private int batteryLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Drone drone;
}