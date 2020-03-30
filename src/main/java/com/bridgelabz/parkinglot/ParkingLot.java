package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.com.exception.ParkingLotException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    public int actualCapacity;
    public List<Object> vehicles;
    ParkingSlot parkingSlot;
    ParkingLotInformer lotInformer;

    public ParkingLot(int capacity) {
       setCapacity(capacity);
        lotInformer=new ParkingLotInformer();
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
    public ArrayList<Integer> getEmptySlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot)==null)
                .forEach(emptySlots::add);
        return emptySlots;
    }

    //Function To Park Vehicle In ParkingLot
    public boolean park(Object vehicle,DriverType driverType) throws ParkingLotException {
        parkingSlot = new ParkingSlot(vehicle);
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked",ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);

        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            lotInformer.getInformedObserver();
            throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
        }

        ArrayList<Integer> emptyList = driverType.getEmptyList(getEmptySlot());
        this.vehicles.set(emptyList.get(0), parkingSlot);
        return true;
    }

    //Function To Confirm Vehicle Is Parked or Not
    public boolean isVehicleParked(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if(this.vehicles.contains(parkingSlot)) return true;
        return false;
    }

    //Function To UnPark Vehicle From ParkingLot
    public boolean unPark(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.contains(parkingSlot)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            return true;
        }
        return false;
    }

    //Function To Find Vehicle In ParkingLot Where It is Parked
    public int findVehicle(Object vehicle) {
        parkingSlot=new ParkingSlot(vehicle);
        if(!this.vehicles.contains(parkingSlot))
            throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return this.vehicles.indexOf(parkingSlot);
    }

    //Function To Find Time Of Vehicle When It Is Park In ParkingLot
    public LocalTime getVehicleParkedTime(Object vehicle)
    {
        ParkingSlot parkingSlot=new ParkingSlot(vehicle);
        return  parkingSlot.time;
    }

}