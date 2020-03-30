package com.bridgelabz.parkinglot;

public class AirportSecurity implements ParkingLotObserver {

    public boolean isFullCapacity;


    @Override
    public void capacityIsFull() {
        isFullCapacity=true;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
