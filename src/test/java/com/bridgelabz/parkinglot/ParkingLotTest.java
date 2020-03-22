package com.bridgelabz.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem=null;
    Object vehicle=null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle =new Object();
    }


    @Test
    public void givenVehical_WhenParked_ShouldReturnTrue() {

        try {
            parkingLotSystem.park(vehicle);
            boolean Isparked = parkingLotSystem.isVehicleParked(vehicle);
            assertTrue(Isparked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVechical_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("parkinglot is full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isUnparked = parkingLotSystem.unPark(vehicle);
            Assert.assertTrue(isUnparked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShoulInformTheOwner() {
        ParkingLotOwner owner=new ParkingLotOwner();
        parkingLotSystem.registerOwner(owner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vechile() {
        Object vehical2=new Object();
        parkingLotSystem.setCapacity(2);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehical2);
            boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
            boolean isParked2 = parkingLotSystem.isVehicleParked(vehical2);

            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {

        }
    }
}
