alter table medication
    add constraint fk_medicationDrone
        foreign key (drone_id) references drone (Id)