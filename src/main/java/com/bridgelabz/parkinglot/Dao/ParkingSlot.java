package com.bridgelabz.parkinglot.Dao;

import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {

    public Vehicle vehicle;
    public LocalTime time;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time=LocalTime.now();
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot parkingSlot = (ParkingSlot) o;
        return Objects.equals(vehicle, parkingSlot.vehicle);
    }

}
