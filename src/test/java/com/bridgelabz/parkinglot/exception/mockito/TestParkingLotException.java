package com.bridgelabz.parkinglot.exception.mockito;


import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class TestParkingLotException {

    Vehicle vehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        vehicle = new Vehicle();
        parkingLot=mock(ParkingLot.class);
    }

    @Test(expected = ParkingLotException.class)
    public void testExceptionClass_WhenParkFunctionCall_ShouldReturnException() {
        doThrow(ParkingLotException.class).when(parkingLot).park(0,vehicle);
        parkingLot.park(0,vehicle);
    }

    @Test
    public void testExceptionClass_WhenPassSameObjectToPark_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (invocation.getArgument(0).equals(0)) {
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
                }
                throw new ParkingLotException("", ParkingLotException.ExceptionType.LOT_IS_FULL);
            }
        }).when(parkingLot).park(0,vehicle);

        try {
            parkingLot.park(0,vehicle);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenPassAnotherObjectToPark_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (invocation.getArgument(0).equals(0)) {
                    throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
                }
                throw new ParkingLotException("", ParkingLotException.ExceptionType.LOT_IS_FULL);
            }
        }).when(parkingLot).park(0,vehicle);

        try {
            parkingLot.park(1,vehicle);
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
            parkingLot.findVehicle(new Vehicle());
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenVehicleNotFoundByGivenColour_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
            }
        }).when(parkingLot).searchVehiclesByColour(any());

        try {
            parkingLot.searchVehiclesByColour("White");
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenVehicleNotFoundByGivenName_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
            }
        }).when(parkingLot).searchVehiclesByName(any());

        try {
            parkingLot.searchVehiclesByName("BMW");
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void testExceptionClass_WhenVehicleNotFoundByGivenNameAndColour_ShouldReturnException() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new ParkingLotException("", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
            }
        }).when(parkingLot).searchVehiclesByNameAndByColour(any(),any());

        try {
            parkingLot.searchVehiclesByNameAndByColour("BMW","Blue");
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

}
