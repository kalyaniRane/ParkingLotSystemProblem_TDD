package com.bridgelabz.parkinglot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public int actualCapacity;
    public List<Vehicle> vehicles;
    private List<ParkingLotObserver> observers;
    public enum DriverType{NORMAL,HANDICAP}
    private DriverType driverType;
    public ParkingLotSystem(int capacity) {
        setCapacity(capacity);
        this.observers = new ArrayList<>();
    }

    //Function to register Observers in List For ParkingLot
    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    //Function To Set A ActualCapacity Of ParkingLot
    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    //Function to Initialize Null Value At The Starting For Understanding ParkingLot is Empty
    public int initializeParkingLot() {
        this.vehicles=new ArrayList<>();
        IntStream.range(0,this.actualCapacity).forEach(slots ->vehicles.add(null));
        return vehicles.size();
    }

    //Function To Find List of EmptySlots Of Parking
    public ArrayList<Integer> getSlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        for (int slot = 0; slot < this.actualCapacity; slot++) {
            if (this.vehicles.get(slot) == null)
                emptySlots.add(slot);
        }
        return emptySlots;
    }

    //Function To Park Vehicle In ParkingLot
    public void park(Vehicle vehicle,DriverType driverType) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");

        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            for (ParkingLotObserver observer : observers)
                observer.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        ArrayList<Integer> emptyList = getEmptyList(driverType);
        parked(emptyList.get(0),vehicle);
    }

    //Function To Park Vehicle With Given Slot
    public void parked(int slot, Vehicle vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("vehicle already parked");
        }
        this.vehicles.set(slot, vehicle);
    }

    public ArrayList<Integer> getEmptyList(DriverType driverType){
        ArrayList<Integer> emptyParkingSlotList = getSlot();
        if(DriverType.NORMAL.equals(driverType))
        Collections.sort(emptyParkingSlotList,Collections.reverseOrder());
        if(DriverType.HANDICAP.equals(driverType))
            Collections.sort(emptyParkingSlotList);
        return emptyParkingSlotList;
    }

    //Function To Confirm Vehicle Is Parked or Not
    public boolean isVehicleParked(Vehicle vehicle) {
        if(this.vehicles.contains(vehicle)) return true;
        return false;
    }

    //Function To UnPark Vehicle From ParkingLot
    public boolean unPark(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            return true;
        }
        return false;
    }

    //Function To Find Vehicle In ParkingLot Where It is Parked
    public int findVehicle(Vehicle vehicle) {
        if(!this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle Not Present");
        return this.vehicles.indexOf(vehicle);
    }

    //Function To Find Time Of Vehicle When It Is Park In ParkingLot
    public LocalTime findTimeOfVehicle(Vehicle vehicle)
    {
        if(!this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle Not Present");
        if(vehicle.getTime() == null)
            throw new ParkingLotException("Vehicle Has Not Set Time");
        return vehicle.getTime();
    }

}
