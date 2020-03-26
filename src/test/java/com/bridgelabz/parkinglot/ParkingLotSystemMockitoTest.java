package com.bridgelabz.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;

public class ParkingLotSystemMockitoTest {

    Vehicle vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle=new Vehicle();
        owner=new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem=mock(ParkingLotSystem.class);
    }

    @Test
    public void givenMockitoTest_WhenCheckOwnerClassWithCheckCapacityIsFull() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                owner.capacityIsFull();
                return null;
            }
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);

        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertTrue(owner.isCapacityFull());
    }

    @Test
    public void givenMockitoTest_WhenCheckOwnerClassWithCapacityAvailable() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                owner.capacityIsAvailable();
                return null;
            }
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);

        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertFalse(owner.isCapacityFull());
    }

    @Test
    public void givenMockitoTest_WhenCheckSecurityClassWithCheckCapacityIsFull() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                airportSecurity.capacityIsFull();
                return null;
            }
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);

        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertTrue(airportSecurity.isCapacityFull());
    }

    @Test
    public void givenMockitoTest_WhenCheckSecurityClassWithCapacityAvailable() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                airportSecurity.capacityIsAvailable();
                return null;
            }
        }).when(parkingLotSystem).park(vehicle, ParkingLotSystem.DriverType.NORMAL);

        parkingLotSystem.park(vehicle, ParkingLotSystem.DriverType.NORMAL);
        Assert.assertFalse(airportSecurity.isCapacityFull());
    }

}
