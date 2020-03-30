package com.bridgelabz.parkinglot.com.exception.mockito;

import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class TestParkingLotSystem {

    Object vehicle;
    ParkingLot parkingLot;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot = mock(ParkingLot.class);
        parkingLotSystem =new ParkingLotSystem(3);
        parkingLotSystem.addLot(parkingLot);
    }

    @Test
    public void testParkedVehicle() {
        when(parkingLot.park(vehicle, ParkingLot.DriverType.NORMAL)).thenReturn(true);
        boolean park = parkingLotSystem.parkedVehicle(vehicle, ParkingLot.DriverType.NORMAL);
        Assert.assertTrue(park);
    }

    @Test
    public void testUnParkedVehicle_ShouldReturnTrue() {
        when(parkingLot.unPark(vehicle)).thenReturn(true);
        boolean unpark = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void testIsVehicleParked_ShouldReturnTrue() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        boolean isPark = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isPark);
    }

    @Test
    public void testFindVehicle_ShouldReturnSlot() {
        when(parkingLot.findVehicle(vehicle)).thenReturn(1);
        int slot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(1,slot);
    }

}
