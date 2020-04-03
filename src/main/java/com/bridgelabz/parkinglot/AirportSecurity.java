package com.bridgelabz.parkinglot;

public class AirportSecurity implements ParkingLotObserver {

    public boolean isFullCapacity;


    @Override
    public void capacityIsFull() {
        isFullCapacity=true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
