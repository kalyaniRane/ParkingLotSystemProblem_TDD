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

    @Test
    public void testSearchVehiclesByColours() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(0);
        list.add(2);
        when(parkingLot.searchVehiclesByColour(any())).thenReturn(list);
        ArrayList<Integer> vehiclesList = parkingLotSystem.searchVehiclesByColour("White");
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchVehiclesByNameAndGetPlateNumber() {
        ArrayList<String> expectedNumber=new ArrayList<>();
        expectedNumber.add("MH19 NJ 56781");
        expectedNumber.add("MH16 OP 123456");
        when(parkingLot.searchVehiclesByNameAndByColour(any(),any())).thenReturn(expectedNumber);
        ArrayList<String> vehicleList = parkingLotSystem.searchVehiclesByNameAndByColour("Toyota", "Blue");
        Assert.assertSame(expectedNumber,vehicleList);
    }

    @Test
    public void testSearchVehiclesByName() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(0);
        list.add(1);
        when(parkingLot.searchVehiclesByName(any())).thenReturn(list);
        ArrayList<Integer> vehiclesList = parkingLotSystem.searchVehiclesByName("BMW");
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchVehiclesWhoseParkLast30Minutes() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(0);
        list.add(1);
        when(parkingLot.searchVehiclesWhoseParkLast30Minutes()).thenReturn(list);
        ArrayList<Integer> vehiclesList = parkingLotSystem.searchVehiclesWhoseParkLast30Minutes();
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchVehiclesDetailsByVehicleTypeAndDriverType() {
        ArrayList<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        when(parkingLot.searchVehicleByDriverTypeAndVehicleType(any(),any())).thenReturn(list);
        ArrayList<String> vehiclesList = parkingLotSystem.searchVehicleByDriverTypeAndVehicleType( DriverType.HANDICAP, VehicleType.SMALL);
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchAllVehiclesDetails() {
        ArrayList<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        when(parkingLot.searchAllVehicles()).thenReturn(list);
        ArrayList<String> vehiclesList = parkingLotSystem.searchAllVehicles();
        Assert.assertEquals(list,vehiclesList);
    }

}