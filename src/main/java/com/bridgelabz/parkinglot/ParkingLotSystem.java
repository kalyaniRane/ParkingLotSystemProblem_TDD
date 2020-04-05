package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.enums.VehicleSortField;
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

    public boolean parkedVehicle(Vehicle vehicle, DriverType driverType, VehicleType vehicleType) {
        lot = getLotHaveMaxSpace(parkingLots);
        return lot.park(vehicle,driverType,vehicleType);
    }

    public boolean unParkedVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            return parkingLot.unPark(vehicle);
        }
        return false;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for(ParkingLot parkingLot : parkingLots)
            return parkingLot.isVehicleParked(vehicle) ;
        return false;
    }

    public int findVehicle(Vehicle vehicle) {
        for(ParkingLot parkingLot: parkingLots)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public LocalTime getVehicleParkedTime(Vehicle vehicle) {
        for(ParkingLot parkingLot: parkingLots)
            return parkingLot.getVehicleParkedTime(vehicle);
        throw new ParkingLotException("Time Not Available", ParkingLotException.ExceptionType.TIME_NOT_AVAILABLE);
    }

    public ArrayList<String> searchVehiclesByGivenFields(VehicleSortField vehicleSortField, String... field){

        for (ParkingLot lot: parkingLots)
            return lot.searchVehiclesByGivenFields(vehicleSortField,field);
        throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

}