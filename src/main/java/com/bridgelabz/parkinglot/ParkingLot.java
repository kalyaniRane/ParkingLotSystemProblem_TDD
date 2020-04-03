package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.Dao.ParkingSlot;
import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    public int actualCapacity;
    public List<ParkingSlot> vehicles;
    ParkingSlot parkingSlot;
    ParkingLotInformer lotInformer;

    public ParkingLot(int capacity) {
        setCapacity(capacity);
        lotInformer=ParkingLotInformer.getInstance();
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
    public ArrayList<Integer> getEmptySlotList() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot)==null)
                .forEach(emptySlots::add);
        return emptySlots;
    }

    //Function To Park Vehicle In ParkingLot
    public boolean park(int emptySlot, Vehicle vehicle, DriverType driverType, VehicleType vehicleType) throws ParkingLotException {

        if (isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked",ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);

        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            lotInformer.getInformedObserver();
            throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
        }
        parkingSlot = new ParkingSlot(vehicle,vehicleType,driverType);
        this.vehicles.set(emptySlot, parkingSlot);
        return true;
    }

    //Function To Confirm Vehicle Is Parked or Not
    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if(this.vehicles.contains(parkingSlot)) return true;
        return false;
    }

    //Function To UnPark Vehicle From ParkingLot
    public boolean unPark(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.contains(parkingSlot)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            return true;
        }
        return false;
    }

    //Function To Find Vehicle In ParkingLot Where It is Parked
    public int findVehicle(Vehicle vehicle) {
        parkingSlot=new ParkingSlot(vehicle);
        if(!this.vehicles.contains(parkingSlot))
            throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return this.vehicles.indexOf(parkingSlot);
    }

    //Function To Find Time Of Vehicle When It Is Park In ParkingLot
    public LocalTime getVehicleParkedTime(Vehicle vehicle)
    {
        parkingSlot=new ParkingSlot(vehicle);
        return  parkingSlot.time;
    }

    //Function To Search Vehicle By Its Colour
    public ArrayList<Integer> searchVehiclesByColour(String colour){
        ArrayList<Integer>vehicleList=new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot).getVehicle().getColour()==colour)
                .forEach(vehicleList::add);

        if(vehicleList.isEmpty())
            throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return vehicleList;
    }

    //Function To Get A Plate Number Of Vehicle Which Sorted By Its Name And Colour
    public ArrayList<String> searchVehiclesByNameAndByColour(String name, String colour){
        ArrayList<String>vehicleList=new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot-> vehicles.get(slot).getVehicle().getVehicleName()==name)
                .filter(slot-> vehicles.get(slot).getVehicle().getColour()==colour)
                .mapToObj(slot->(slot+" "+ vehicles.get(slot).getVehicle().getPlateNumber()))
                .collect(Collectors.toList())
                .forEach(vehicleList::add);

        if(vehicleList.isEmpty())
            throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return vehicleList;
    }

    //Function To Search Vehicle By Its Name
    public ArrayList<Integer> searchVehiclesByName(String name){
        ArrayList<Integer>vehicleList=new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot).getVehicle().getVehicleName()==name)
                .forEach(vehicleList::add);

        if(vehicleList.isEmpty())
            throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return vehicleList;
    }

    //Function To Search Vehicle By Its Name
    public ArrayList<Integer> searchVehiclesWhoseParkLast30Minutes(){
        ArrayList<Integer>vehicleList=new ArrayList<>();
        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot).getTime().getMinute()-LocalTime.now().getMinute()<=30)
                .forEach(vehicleList::add);
        if(vehicleList.isEmpty())
            throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return vehicleList;
    }

    //Function To Search Vehicles By DriverType And Vehicle Type
    public ArrayList<String> searchVehicleByDriverTypeAndVehicleType(DriverType driverType , VehicleType vehicleType){
        ArrayList<String> vehicleDetails=new ArrayList<>();

        IntStream.range(0,actualCapacity)
                .filter(slot->vehicles.get(slot)!=null)
                .filter(slot-> vehicles.get(slot).getVehicleType1()==vehicleType)
                .filter(slot->vehicles.get(slot).getDriver()==driverType)
                .mapToObj(slot->(vehicles.get(slot).getVehicle().getVehicleName()+" "+vehicles.get(slot).getVehicle().getColour()+" "+vehicles.get(slot).getVehicle().getPlateNumber()))
                .forEach(vehicleDetails::add);

        return vehicleDetails;
    }



}