package com.bridgelabz.parkinglot.exception.mockito;

import com.bridgelabz.parkinglot.*;
import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestParkingLotInformer {
    @Mock
    ParkingLot parkingLot;

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    ParkingLotInformer lotInformer;
    Vehicle vehicle;
    ParkingLotObserver owner;
    ParkingLotObserver airportSecurity;

    @Before
    public void setUp() throws Exception {
        parkingLot=mock(ParkingLot.class);
        lotInformer=ParkingLotInformer.getInstance();
        vehicle=new Vehicle();
        owner=new ParkingLotOwner();
        airportSecurity=new AirportSecurity();
    }

    @Test
    public void testGetInformedObserver() {
        lotInformer.registerParkingLotObserver(owner);
        lotInformer.registerParkingLotObserver(airportSecurity);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                lotInformer.getInformedObserver();
                return true;
            }
        }).when(parkingLot).park(0,vehicle, DriverType.HANDICAP, VehicleType.SMALL);

        parkingLot.park(0,vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        Assert.assertTrue(owner.isCapacityFull() && airportSecurity.isCapacityFull());
    }

}
