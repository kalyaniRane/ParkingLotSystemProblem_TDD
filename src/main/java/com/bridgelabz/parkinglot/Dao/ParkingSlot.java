package com.bridgelabz.parkinglot.Dao;

import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {

    Vehicle vehicle;
    public LocalTime time;
    public DriverType driver;
    public VehicleType vehicleType1;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time=LocalTime.now();
    }

    public ParkingSlot(Vehicle vehicle, VehicleType vehicleType,DriverType driverType ) {
        this.driver=driverType;
        this.vehicleType1 =vehicleType;
        this.vehicle = vehicle;
        this.time=LocalTime.now();
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public DriverType getDriver() {
        return driver;
    }

    public VehicleType getVehicleType1() {
        return vehicleType1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot parkingSlot = (ParkingSlot) o;
        return Objects.equals(vehicle, parkingSlot.vehicle);
    }

}
