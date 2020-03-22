package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int actualCapacity;
    private List vehicles;
    private ParkingLotOwner owner;

    public ParkingLotSystem(int capacity) {
        this.vehicles=new ArrayList<>();
        this.actualCapacity = capacity;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner=owner;
    }
    public void setCapacity(int capacity) {
        this.actualCapacity=capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.actualCapacity){
            owner.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");
        this.vehicles.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if(this.vehicles.contains(vehicle)) return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if ( vehicle == null)  return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            return true;
        }
        return false;
    }

}
