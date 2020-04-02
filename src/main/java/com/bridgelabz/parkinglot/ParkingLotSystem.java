package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.enums.DriverType;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    List<ParkingLot> parkingLots;
    ParkingLot lot;
    public ParkingLotSystem() {
          this.parkingLots = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLot parkingLot) {
        if (this.parkingLots.contains(parkingLot)) return true;
        return false;
    }

    public ParkingLot getLotHaveMaxSpace(List<ParkingLot> parkingLots) {
        return parkingLots.stream().sorted(Comparator.comparing(list -> list.getEmptySlotList().size(), Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }

    public boolean parkedVehicle(Object vehicle, DriverType driverType,VehicleType vehicleType) {
        lot = getLotHaveMaxSpace(parkingLots);
        int emptySlot = 0;
        try {
            ArrayList<Integer> emptyList = driverType.getEmptyList(lot.getEmptySlotList());
            Collections.sort(emptyList);
            emptySlot = vehicleType.getEmptySlot(emptyList);
            return lot.park(emptySlot, vehicle);
        }catch (ParkingLotException e){
           lot.park(emptySlot,vehicle);
        }
        throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
    }

    public boolean unParkedVehicle(Object vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            return parkingLot.unPark(vehicle);
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        for(ParkingLot parkingLot : parkingLots)
            if(parkingLot.isVehicleParked(vehicle))
                return true;
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