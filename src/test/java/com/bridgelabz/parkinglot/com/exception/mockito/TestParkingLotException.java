package com.bridgelabz.parkinglot.com.exception.mockito;


import com.bridgelabz.parkinglot.DriverType;
import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.com.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class TestParkingLotException {

    Object vehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLot=mock(ParkingLot.class);
    }

    @Test(expected = ParkingLotException.class)
    public void testExceptionClass_WhenParkFunctionCall_ShouldReturnException() {
        doThrow(ParkingLotException.class).when(parkingLot).park(vehicle, DriverType.NORMAL);
        parkingLot.park(vehicle, DriverType.NORMAL);
    }

    @Test
    public void testExceptionClass_WhenPassSameObjectToPark_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (invocation.getArgument(0).equals(vehicle)) {
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
                }
                throw new ParkingLotException("", ParkingLotException.ExceptionType.LOT_IS_FULL);
            }
        }).when(parkingLot).park(vehicle,DriverType.NORMAL);

        try {
            parkingLot.park(vehicle, DriverType.NORMAL);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenPassAnotherObjectToPark_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (invocation.getArgument(0).equals(vehicle)) {
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
                }
                throw new ParkingLotException("", ParkingLotException.ExceptionType.LOT_IS_FULL);
            }
        }).when(parkingLot).park(any(),any());

        try {
            parkingLot.park(new Object(), DriverType.NORMAL);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenAnotherObjectPassForFindVehicle_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
            }
        }).when(parkingLot).findVehicle(vehicle);

        try {
            parkingLot.findVehicle(new Object());
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

}
