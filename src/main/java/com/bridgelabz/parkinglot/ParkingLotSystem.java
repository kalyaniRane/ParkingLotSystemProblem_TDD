package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.com.exception.ParkingLotException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    int lotCapacity;
    List<ParkingLot> parkingLots;

    public ParkingLotSystem(int lotCapacity) {
        this.lotCapacity = lotCapacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.parkingLots = new ArrayList<>();
        IntStream.range(0, this.lotCapacity).forEach(lots -> parkingLots.add(null));
        return parkingLots.size();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLots.set(getEmptyLots().get(0), parkingLot);
    }

    public boolean isLotAdded(ParkingLot parkingLot) {
        if (this.parkingLots.contains(parkingLot)) return true;
        return false;
    }

    public ArrayList<Integer> getEmptyLots() {
        ArrayList<Integer> emptyLots = new ArrayList<>();
        IntStream.range(0,lotCapacity)
                .filter(slot->parkingLots.get(slot)==null)
                .forEach(emptyLots::add);
        return emptyLots;
    }

    public boolean parkedVehicle(Object vehicle,DriverType driverType) {
        for(ParkingLot parkingLot : parkingLots){
            return parkingLot.park(vehicle, driverType);
        }
        return false;
    }

    public boolean unParkedVehicle(Object vehicle) {
        for (ParkingLot parkingLot :parkingLots) {
            return parkingLot.unPark(vehicle);
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        for(ParkingLot parkingLot: parkingLots)
            return parkingLot.isVehicleParked(vehicle);
        return false;
    }

    public int findVehicle(Object vehicle) {
        for(ParkingLot parkingLot: parkingLots)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public LocalTime getVehicleParkedTime(Object vehicle) {
        for(ParkingLot parkingLot: parkingLots)
            return parkingLot.getVehicleParkedTime(vehicle);
        throw new ParkingLotException("Time Not Available", ParkingLotException.ExceptionType.TIME_NOT_AVAILABLE);
    }
}