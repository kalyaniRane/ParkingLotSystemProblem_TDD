package com.bridgelabz.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.AccountExpiredException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem=null;
    Object vehicle=null;
    Object vehicle1=null;
    List expectedList;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle =new Object();
        vehicle1 =new Object();
        expectedList = new ArrayList();
    }

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

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
            parkingLotSystem.park(vehicle);
            boolean isUnParked = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner=new ParkingLotOwner();
        parkingLotSystem.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
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

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

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
        parkingLotSystem.parked(0, vehicle);
        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void AfterParkAndUnParkVehicles_ShouldReturnEmptySlots() {
        expectedList.add(0);
        expectedList.add(2);
        parkingLotSystem.setCapacity(3);
        parkingLotSystem.parked(0, vehicle);
        parkingLotSystem.parked(1,vehicle1);
        parkingLotSystem.unPark(vehicle);
        ArrayList emptySlotList = parkingLotSystem.getSlot();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingLotSystem.setCapacity(10);
        parkingLotSystem.initializeParkingLot();
        ArrayList<Integer> emptySlotList = parkingLotSystem.getSlot();
        parkingLotSystem.parked(emptySlotList.get(1), vehicle);
        boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

}
