package com.bridgelabz.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue(){
        boolean isParked = parkingLotSystem.park(new Object());
        assertTrue(isParked);
    }
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        Object vehicle =new Object();
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.unPark(vehicle);
        assertTrue(isUnParked);
    }

}
