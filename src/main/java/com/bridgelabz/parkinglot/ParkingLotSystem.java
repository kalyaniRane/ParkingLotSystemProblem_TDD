package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private int actualCapacity;
    public List<Object> vehicles;
    //private ParkingLotOwner owner;
    private List<ParkingLotObserver> observers;
    //private AirportSecurity airportSecurity;
    private int slot=0;


    public ParkingLotSystem(int capacity) {
        setCapacity(capacity);
        this.observers = new ArrayList<>();
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.vehicles=new ArrayList<>();
        IntStream.range(0,this.actualCapacity).forEach(slots ->vehicles.add(null));
        return vehicles.size();
    }

    public ArrayList<Integer> getSlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        for (int slot = 0; slot < this.actualCapacity; slot++) {
            if (this.vehicles.get(slot) == null)
                emptySlots.add(slot);
        }
        return emptySlots;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");

        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            for (ParkingLotObserver observer : observers)
                observer.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        //this.vehicles.add(vehicle);
        parked(slot++, vehicle);
    }

    public void parked(int slot, Object vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("VEHICLE ALREADY PARK");
        }
        this.vehicles.set(slot, vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if(this.vehicles.contains(vehicle)) return true;
        return false;
    }

    public boolean unPark(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            //this.vehicles.remove(vehicle);
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            return true;
        }
        return false;
    }

}
