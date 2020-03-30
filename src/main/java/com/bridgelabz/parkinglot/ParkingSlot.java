package com.bridgelabz.parkinglot;

import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {

    Object vehicle;
    public LocalTime time;

    public ParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
        this.time=LocalTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot parkingSlot = (ParkingSlot) o;
        return Objects.equals(vehicle, parkingSlot.vehicle);
    }

}
