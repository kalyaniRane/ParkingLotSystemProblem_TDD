package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.com.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem=null;
    ParkingLotInformer lotInformer=null;
    ParkingLot parkingLot,parkingLot1,parkingLot2,parkingLot3;
    Object vehicle=null;
    Object vehicle1=null;
    List expectedList;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLotSystem = new ParkingLotSystem(3);
        lotInformer = new ParkingLotInformer();
        vehicle =new Object();
        vehicle1 =new Object();
        expectedList = new ArrayList();
        parkingLotSystem.addLot(parkingLot);
    }

    @Test
    public void givenParkingLot_WhenAdded_ShouldReturnTrue() {
        parkingLot1 = new ParkingLot(2);
        parkingLotSystem.addLot(parkingLot1);
        boolean lotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertTrue(lotAdded);
    }

    //UC1
    @Test
    public void givenVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED,e.type);
        }
    }

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            boolean isUnParked = parkingLotSystem.unParkedVehicle(vehicle);
            Assert.assertTrue(isUnParked);
    }

    //UC3
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        Object vehicle2=new Object();
        ParkingLotOwner owner=new ParkingLotOwner();
        lotInformer.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
            parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
            boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
            Assert.assertTrue(isParked1 && isParked2);
    }

    //UC4
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        lotInformer.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    //UC5
    @Test
    public void givenParkingLot_HasSpace_ShouldReturnFalse() {
        ParkingLotOwner owner=new ParkingLotOwner();
        lotInformer.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
            parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
        } catch (ParkingLotException e) {
            parkingLotSystem.unParkedVehicle(vehicle);
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_WhenInitialize_ShouldReturnParkingCapacity() {
        parkingLot.setCapacity(10);
        int parkingLotCapacity = parkingLot.initializeParkingLot();
        Assert.assertEquals(10,parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        expectedList.add(0);
        expectedList.add(1);
        parkingLot.setCapacity(2);
        ArrayList emptySlotList = parkingLot.getEmptySlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() {
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void AfterParkAndUnParkVehicles_ShouldReturnEmptySlots() {
        expectedList.add(0);
        expectedList.add(2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
        parkingLotSystem.unParkedVehicle(vehicle);
        ArrayList emptySlotList = parkingLot.getEmptySlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

    //UC7
    @Test
    public void givenVehicle_WhenPresent_ShouldReturnSlot() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.HANDICAP);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
        int vehicleSlot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0,vehicleSlot);
    }

    @Test
    public void givenVehicle_WhenNotPresent_ShouldReturnException() {
        try {
            parkingLot.setCapacity(5);
            parkingLotSystem.parkedVehicle(vehicle,DriverType.NORMAL);
            parkingLotSystem.findVehicle(vehicle1);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC8
    @Test
    public void givenVehicle_WhenParkWithTime_ShouldReturnParkingTime() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        ParkingSlot parkingSlot=new ParkingSlot(vehicle);
        LocalTime time = parkingSlot.time;
        LocalTime timeOfVehicle = parkingLotSystem.getVehicleParkedTime(vehicle);
        Assert.assertEquals(time,timeOfVehicle);
    }

    //UC9
    @Test
    public void givenVehicle_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        Object vehicle2=new Object();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

    //UC10
    @Test
    public void givenDriverTypeHandicap_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        Object vehicle2=new Object();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.HANDICAP);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.HANDICAP);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.HANDICAP);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

    @Test
    public void givenDriverTypeHandicap_WhenParkedAtNearestSlot_ShouldReturnTrue() {
        Object vehicle2=new Object();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.HANDICAP);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

}