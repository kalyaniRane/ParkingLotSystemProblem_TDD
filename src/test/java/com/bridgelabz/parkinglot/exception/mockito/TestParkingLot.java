package com.bridgelabz.parkinglot.exception.mockito;

import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.ParkingLotSystem;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestParkingLot {

    @Mock
    ParkingLotSystem parkingLotSystem;

    @Rule
    public MockitoRule  mockitoRule= MockitoJUnit.rule();

    ParkingLot  parkingLot;
    Vehicle vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem=mock(ParkingLotSystem.class);
        parkingLot=new ParkingLot(2);
        vehicle=new Vehicle();
    }

    @Test
    public void parkFunctionTest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                parkingLot.park(any(),any(),any(),any());
                return true;
            }
        }).when(parkingLotSystem).parkedVehicle(vehicle, DriverType.HANDICAP, VehicleType.SMALL);
        boolean vehicleParked = parkingLot.park(0,vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        Assert.assertTrue(vehicleParked);
    }
}
