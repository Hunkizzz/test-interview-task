
The application is using H2 as the database. H2 is a lightweight, open source, in-memory database engine that can be used in embedded mode. This means that it does not require any special action to run the application; it is already included in the application's classpath and can be accessed using the provided configuration. The application is configured to use H2 in an embedded mode, which means that all data is stored in memory and is destroyed when the application is stopped. H2 provides a web-based console for accessing and manipulating the data stored in the database. The console is enabled by setting the `h2.console.enabled` property to `true`, and can be accessed at the `/h2-console` path. The username and password can be configured by setting the `spring.datasource.username` and `spring.datasource.password` properties.

You can use these requests to check the working of the application:

Medication-controller:

Set the image to the medication:

/medication/image 
{
  "uuid": "ff0f1b2d-fb7f-4a8a-8b9c-d1cd7f5d0f8f",
  "imageNumber": "1"
}

Drone-controller:

Register drone

/drone/register
{
  "id": "2c1d3bd2-73c1-47f4-b17e-4d0c16f9bff5",
  "serialNumber": "t2WZKS5fWfqgy51JUAVF8",
  "model": "LIGHTWEIGHT",
  "weightLimit": 500,
  "batteryCapacity": 1,
  "state": "IDLE"
}

get the drone with the information about loaded medications

/drone/{droneId}/medications

droneId = dafb1a60-c953-4baa-b8e8-908b7b8f1b0a

get the information about battery level

/drone/battery/{droneId}

droneId = dafb1a60-c953-4baa-b8e8-908b7b8f1b0a

get drones with IDLE state

/drone/available

No parameters

delivery-controller

load drone with the medications

/delivery/load

{
  "droneId": "dafb1a60-c953-4baa-b8e8-908b7b8f1b0a",
  "medicationIds": [
    "d6c37e3f-3dcc-4d7a-b3d4-4b3c3b4fcbeb","3b3dc2bc-9e7f-4f9f-97b3-c753f5d0ef8f"
  ]
}

send the drone to deliver the medications
/delivery/deliver/{droneId}

droneId = dafb1a60-c953-4baa-b8e8-908b7b8f1b0a

return drone to the station

/delivery/return/{droneId}

droneId = dafb1a60-c953-4baa-b8e8-908b7b8f1b0a

