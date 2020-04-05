package com.bridgelabz.parkinglot.exception.mockito;

import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.ParkingLotSystem;
import com.bridgelabz.parkinglot.enums.VehicleSortField;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class TestParkingLotSystem {

    @Mock
    ParkingLot parkingLot;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Vehicle vehicle;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle = new Vehicle();
        parkingLotSystem =new ParkingLotSystem();
        parkingLotSystem.addLot(parkingLot);
    }

    @Test
    public void testParkedVehicle_ShouldReturnTrue() {
        when(parkingLot.park(any(),any(),any())).thenReturn(true);
        boolean ispark = parkingLotSystem.parkedVehicle(vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        Assert.assertTrue(ispark);
    }

    @Test
    public void testParkedVehicle_ShouldReturnFalse() {
        when(parkingLot.park(any(),any(),any())).thenReturn(false);
        boolean ispark = parkingLotSystem.parkedVehicle(vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        Assert.assertFalse(ispark);
    }

    @Test
    public void testUnParkedVehicle_ShouldReturnTrue() {
        when(parkingLot.unPark(vehicle)).thenReturn(true);
        boolean unpark = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void testUnParkedVehicle_ShouldReturnFalse() {
        when(parkingLot.unPark(vehicle)).thenReturn(false);
        boolean unpark = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertFalse(unpark);
    }

    @Test
    public void testIsVehicleParked_ShouldReturnTrue() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        boolean isPark = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isPark);
    }

    @Test
    public void testIsVehicleParked_ShouldReturnFalse() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(false);
        boolean isPark = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertFalse(isPark);
    }

    @Test
    public void testFindVehicle_ShouldReturnSlot() {
        when(parkingLot.findVehicle(vehicle)).thenReturn(1);
        int slot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(1,slot);
    }

    @Test
    public void testFindVehicle_ShouldReturnException() {
        when(parkingLot.findVehicle(vehicle)).thenThrow(new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND));
        try{
            parkingLotSystem.findVehicle(vehicle);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void testSearchVehiclesByGivenFields() {
        ArrayList<String> list=new ArrayList<>();
        list.add("0 BMW White MH19 AB 567");
        list.add("2 Toyoto Red MH18 GH 4567");
        when(parkingLot.searchVehiclesByGivenFields(any(),any())).thenReturn(list);
        ArrayList<String> vehiclesList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.COLOUR,"White");
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchVehiclesByGivenFields_ShouldReturnException() {
        when(parkingLot.searchVehiclesByGivenFields(any(),any())).thenThrow(new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND));
        try{
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.COLOUR,"White");
        }catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

}
