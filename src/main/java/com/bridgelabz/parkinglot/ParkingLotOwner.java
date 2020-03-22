package com.bridgelabz.parkinglot;

public class ParkingLotOwner {

    public boolean isFullCapacity;

    public void capacityIsFull() {
        isFullCapacity=true;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
