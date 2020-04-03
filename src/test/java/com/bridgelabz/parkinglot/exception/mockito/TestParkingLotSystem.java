package com.bridgelabz.parkinglot.exception.mockito;

import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.ParkingLotSystem;
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

}