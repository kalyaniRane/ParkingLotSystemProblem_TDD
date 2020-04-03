package com.bridgelabz.parkinglot.Dao;

public class Vehicle {
    String colour;
    String vehicleName;
    String plateNumber;

    public Vehicle(String colour, String vehicleName, String plateNumber) {
        this.colour=colour;
        this.vehicleName=vehicleName;
        this.plateNumber=plateNumber;
    }

    public Vehicle() {
    }

    public String getColour() {
        return colour;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

}
