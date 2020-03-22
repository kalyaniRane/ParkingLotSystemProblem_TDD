package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int actualCapacity;
    private List vehicles;
    private ParkingLotOwner owner;
    private List<ParkingLotObserver> observers;
    private AirportSecurity airportSecurity;

    public ParkingLotSystem(int capacity) {
        this.vehicles=new ArrayList<>();
        this.actualCapacity = capacity;
        this.observers=new ArrayList<>();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity=capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");

        if (this.vehicles.size() == this.actualCapacity){
            for (ParkingLotObserver observer:observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException("parkinglot is full");
        }
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
