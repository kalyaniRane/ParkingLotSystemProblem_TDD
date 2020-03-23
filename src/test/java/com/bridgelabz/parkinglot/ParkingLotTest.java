package com.bridgelabz.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem=null;
    Vehicle vehicle=null;
    Vehicle vehicle1=null;
    List expectedList;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle =new Vehicle();
        vehicle1 =new Vehicle();
        expectedList = new ArrayList();
    }

    //UC1
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            assertTrue(isParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVehicle_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("vehicle already parked", e.getMessage());
        }
    }

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
            parkingLotSystem.park(vehicle);
            boolean isUnParked = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(isUnParked);
    }

    //UC3
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner=new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle1);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vehicle() {
        parkingLotSystem.setCapacity(2);
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle1);
            boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
            Assert.assertTrue(isParked1 && isParked2);
    }

    //UC4
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle1);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    //UC5
    @Test
    public void givenParkingLot_HasSpace_ShouldReturnFalse() {
        ParkingLotOwner owner=new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle1);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark(vehicle);
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_WhenInitialize_ShouldReturnParkingCapacity() {
        parkingLotSystem.setCapacity(10);
        int parkingLotCapacity = parkingLotSystem.initializeParkingLot();
        Assert.assertEquals(10,parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        expectedList.add(0);
        expectedList.add(1);
        parkingLotSystem.setCapacity(2);
        parkingLotSystem.initializeParkingLot();
        ArrayList emptySlotList = parkingLotSystem.getSlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() {
        parkingLotSystem.setCapacity(3);
        parkingLotSystem.park(vehicle);
        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void AfterParkAndUnParkVehicles_ShouldReturnEmptySlots() {
        expectedList.add(0);
        expectedList.add(2);
        parkingLotSystem.setCapacity(3);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.unPark(vehicle);
        ArrayList emptySlotList = parkingLotSystem.getSlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingLotSystem.setCapacity(10);
        parkingLotSystem.initializeParkingLot();
        parkingLotSystem.park(vehicle);
        boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

    //UC7
    @Test
    public void givenVehicle_WhenPresent_ShouldReturnSlot() {
        parkingLotSystem.setCapacity(5);
        parkingLotSystem.initializeParkingLot();
        parkingLotSystem.parked(0,vehicle);
        parkingLotSystem.parked(1,vehicle1);
        int vehicleSlot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0,vehicleSlot);
    }

    @Test
    public void givenVehicle_WhenNotPresent_ShouldReturnException() {
        try {
            parkingLotSystem.setCapacity(5);
            parkingLotSystem.initializeParkingLot();
            parkingLotSystem.park(vehicle);
            parkingLotSystem.findVehicle(vehicle1);
        }catch (ParkingLotException e){
            Assert.assertEquals("Vehicle Not Present",e.getMessage());
        }

    }

    //UC8
    @Test
    public void givenVehicle_WhenParkWithTime_ShouldReturnParkingTime() {
        parkingLotSystem.setCapacity(5);
        parkingLotSystem.initializeParkingLot();
        vehicle.setTime();
        parkingLotSystem.parked(0,vehicle);
        LocalTime time = vehicle.getTime();
        LocalTime timeOfVehicle = parkingLotSystem.findTimeOfVehicle(vehicle);
        Assert.assertEquals(time,timeOfVehicle);
    }

    @Test
    public void givenVehicle_WhenParkWithoutTime_ShouldReturnException() {
        try {
            parkingLotSystem.setCapacity(5);
            parkingLotSystem.initializeParkingLot();
            parkingLotSystem.park(vehicle);
            parkingLotSystem.findTimeOfVehicle(vehicle);
        }catch (ParkingLotException e){
            Assert.assertEquals("Vehicle Has Not Set Time",e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        Vehicle vehicle2=new Vehicle();
        parkingLotSystem.setCapacity(3);
        parkingLotSystem.initializeParkingLot();
        parkingLotSystem.park(vehicle);
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.park(vehicle2);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

}