package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.Dao.ParkingSlot;
import com.bridgelabz.parkinglot.Dao.Vehicle;
import com.bridgelabz.parkinglot.enums.VehicleSortField;
import com.bridgelabz.parkinglot.enums.VehicleType;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.enums.DriverType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem=null;
    ParkingLotInformer lotInformer=null;
    ParkingLot parkingLot,parkingLot1;
    Vehicle vehicle=null;
    Vehicle vehicle1=null;
    List expectedList;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLotSystem = new ParkingLotSystem();
        lotInformer = ParkingLotInformer.getInstance();
        vehicle =new Vehicle("White","Toyota","MH19 AB 2341");
        vehicle1 =new Vehicle("Red","Swift","MH20 GH 4563");
        expectedList = new ArrayList();
        parkingLotSystem.addLot(parkingLot);
    }

    @Test
    public void givenParkingLot_WhenAdded_ShouldReturnTrue() {
        parkingLot1 = new ParkingLot(2);
        parkingLotSystem.addLot(parkingLot1);
        boolean lotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertTrue(lotAdded);
    }

    @Test
    public void givenParkingLot_WhenAdded_ShouldReturnFalse() {
        parkingLot1 = new ParkingLot(2);
        boolean lotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertFalse(lotAdded);
    }

    //UC1
    @Test
    public void givenVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL, VehicleType.SMALL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(isParked);
    }


    @Test
    public void givenVehicle_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED,e.type);
        }
    }

    //UC2
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        boolean isUnParked = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnFalse() {
        boolean isUnParked = parkingLotSystem.unParkedVehicle(vehicle);
        Assert.assertFalse(isUnParked);
    }

    //UC3
    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        Vehicle vehicle2=new Vehicle();
        ParkingLotOwner owner=new ParkingLotOwner();
        lotInformer.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL,VehicleType.SMALL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            assertTrue(capacityFull);
        }
    }

    //UC4
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        lotInformer.registerParkingLotObserver(airportSecurity);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    //UC5
    @Test
    public void givenParkingLot_HasSpace_ShouldReturnFalse() {
        ParkingLotOwner owner=new ParkingLotOwner();
        lotInformer.registerParkingLotObserver(owner);
        try {
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        } catch (ParkingLotException e) {
            parkingLotSystem.unParkedVehicle(vehicle);
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC6
    @Test
    public void givenParkingLot_WhenInitialize_ShouldReturnParkingCapacity() {
        parkingLot.setCapacity(10);
        int parkingLotCapacity = parkingLot.initializeParkingLot();
        Assert.assertEquals(10,parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        expectedList.add(0);
        expectedList.add(1);
        parkingLot.setCapacity(2);
        ArrayList emptySlotList = parkingLot.getEmptySlotList();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() {
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        boolean vehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void AfterParkAndUnParkVehicles_ShouldReturnEmptySlots() {
        expectedList.add(0);
        expectedList.add(2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.unParkedVehicle(vehicle);
        ArrayList emptySlotList = parkingLot.getEmptySlotList();
        Assert.assertEquals(expectedList, emptySlotList);
    }

    @Test
    public void givenVehicleForParkingOnEmptySlot_WhenParkWithProvidedEmptySlot_ShouldReturnTrue() {
        parkingLot.setCapacity(10);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        boolean vehiclePark = parkingLotSystem.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

    //UC7
    @Test
    public void givenVehicle_WhenPresent_ShouldReturnSlot() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        int vehicleSlot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0,vehicleSlot);
    }

    @Test
    public void givenVehicle_WhenNotPresent_ShouldReturnException() {
        try {
            parkingLot.setCapacity(5);
            parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
            parkingLotSystem.findVehicle(vehicle1);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC8
    @Test
    public void givenVehicle_WhenParkWithTime_ShouldReturnParkingTime() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        ParkingSlot parkingSlot=new ParkingSlot(vehicle);
        LocalTime time = parkingSlot.time;
        LocalTime timeOfVehicle = parkingLotSystem.getVehicleParkedTime(vehicle);
        Assert.assertEquals(time,timeOfVehicle);
    }

    //UC9
    @Test
    public void givenVehicle_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        Vehicle vehicle2=new Vehicle();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1,DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

    //UC10
    @Test
    public void givenDriverTypeHandicap_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        Vehicle vehicle2=new Vehicle();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

    @Test
    public void givenDriverTypeHandicap_WhenParkedAtNearestSlot_ShouldReturnTrue() {
        Vehicle vehicle2=new Vehicle();
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLotSystem.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked&&isVehicleParked1&&isVehicleParked2);
    }

    //UC11
    @Test
    public void givenLargeVehicle_WhenParkByEvenlyDistribution_ShouldReturnTrue() {
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle,DriverType.NORMAL,VehicleType.LARGE);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isVehicleParked);
    }

    @Test
    public void givenLargeVehicle_WhenParkByEvenlyDistribution_ShouldReturnEmptySlotAfterLargeVehiclePark() {
        int expectedSlot=1;
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle,DriverType.NORMAL,VehicleType.LARGE);
        parkingLotSystem.isVehicleParked(vehicle);
        int emptySlot = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(expectedSlot,emptySlot);
    }

    //UC12
    @Test
    public void givenParkingLot_WhenParkedWhiteVehicles_ShouldReturnListOfVehicles() {
        ArrayList<String> expectedList=new ArrayList<>();
        Vehicle vehicle2 = new Vehicle("White","Swift","MH18 BN 78963");
        expectedList.add("0 Toyota White MH19 AB 2341");
        expectedList.add("2 Swift White MH18 BN 78963");
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL,VehicleType.SMALL);
        ArrayList sortedVehicleList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.COLOUR,"White");
        Assert.assertEquals(expectedList, sortedVehicleList);
    }

    @Test
    public void givenParkingLot_WhenNoOneWhiteVehiclesParked_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("Red","Swift Desire","MH17 OP 98765");
        Vehicle vehicle3 = new Vehicle("Black","Honda","MH18 BH 845621");
        parkingLot.setCapacity(3);
        try {
            parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.COLOUR,"White");
        }catch (ParkingLotException e)
        {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }


    //UC13
    @Test
    public void givenVehicles_WhenParkedWithBlueColorAndToyota_ShouldReturnListOfPlateNumbers() {
        ArrayList<String> expectedNumber=new ArrayList<>();
        Vehicle vehicle2=new Vehicle("Blue","Toyota","MH19 NJ 56781");
        Vehicle vehicle3 =new Vehicle("Blue","Toyota","MH16 OP 123456");
        expectedNumber.add("0 Toyota Blue MH19 NJ 56781");
        expectedNumber.add("1 Toyota Blue MH16 OP 123456");
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle3,DriverType.NORMAL,VehicleType.SMALL);
        ArrayList<String> sortedVehicleList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.NAME_COLOUR,"Toyota","Blue");
        Assert.assertEquals(expectedNumber,sortedVehicleList);
    }

    @Test
    public void givenParkingLot_WhenParkedWithoutBlueColorAndToyota_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("Red","Swift Desire","MH17 OP 98765");
        Vehicle vehicle3 = new Vehicle("Black","Honda","MH18 BH 845621");
        parkingLot.setCapacity(3);
        try {
            parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.NAME_COLOUR,"Toyota","Blue");
        }catch (ParkingLotException e)
        {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC14
    @Test
    public void givenParkingLot_WhenParkedABMWVehicle_ShouldReturnListOfSlots() {
        ArrayList<String> expectedList=new ArrayList<>();
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        Vehicle vehicle3 = new Vehicle("Black","BMW","MH20 TY 01210");
        expectedList.add("0 BMW White MH18 BN 78963");
        expectedList.add("2 BMW Black MH20 TY 01210");
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle3,DriverType.NORMAL,VehicleType.SMALL);
        ArrayList sortedVehicleList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.NAME,"BMW");
        Assert.assertEquals(expectedList, sortedVehicleList);
    }

    @Test
    public void givenParkingLot_WhenNoOneBMWVehicleParked_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("Red","Swift Desire","MH17 OP 98765");
        Vehicle vehicle3 = new Vehicle("Black","Honda","MH18 BH 845621");
        parkingLot.setCapacity(3);
        try {
            parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle3, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.NAME,"BMW");
        }catch (ParkingLotException e)
        {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC15
    @Test
    public void givenParkingLot_WhenParkedBefore30Minutes_ShouldReturnListOfSlots() {
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        Vehicle vehicle3 = new Vehicle("Black","BMW","MH20 TY 01210");
        expectedList.add("0 BMW White MH18 BN 78963");
        expectedList.add("1 Swift Red MH20 GH 4563");
        expectedList.add("2 BMW Black MH20 TY 01210");
        parkingLot.setCapacity(3);
        parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle1, DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle3,DriverType.NORMAL,VehicleType.SMALL);
        ArrayList sortedVehicleList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.TIME_IN_MINUTES,"30");
        Assert.assertEquals(expectedList, sortedVehicleList);
    }

    @Test
    public void givenParkingLot_WhenNoOneVehicleParkedBefore30Minutes_ShouldReturnException() {
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        try {
            parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.unParkedVehicle(vehicle2);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.TIME_IN_MINUTES,"30");
        }catch (ParkingLotException e){
            System.out.println("abcd");
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC16
    @Test
    public void givenDriverTypeAndVehicleType_WhenVehicleParked_ShouldReturnDetails() {
        List<String> expectedList=new ArrayList<>();
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        Vehicle vehicle3 = new Vehicle("Red","Swift","MH20 TY 01210");
        Vehicle vehicle4 = new Vehicle("Wine","Honda","MH19 BO 4920");
        Vehicle vehicle5 = new Vehicle("Black","Volvo","MH24 AE 84235");
        Vehicle vehicle6 = new Vehicle("Blue","Suzuki","MH17 UH 32541");
        parkingLot.setCapacity(10);
        expectedList.add("0 Toyota White MH19 AB 2341");
        expectedList.add("2 Swift Red MH20 TY 01210");
        expectedList.add("4 Volvo Black MH24 AE 84235");
        expectedList.add("5 Suzuki Blue MH17 UH 32541");

        parkingLotSystem.parkedVehicle(vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL,VehicleType.LARGE);
        parkingLotSystem.parkedVehicle(vehicle3,DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle4,DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle5,DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle6,DriverType.HANDICAP,VehicleType.SMALL);
        List<String> vehiclesDetails = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.DRIVER_VEHICLE_TYPE,"SMALL","HANDICAP");
        Assert.assertEquals(expectedList,vehiclesDetails);
    }

    @Test
    public void givenDriverTypeAndVehicleType_WhenVehicleNotParked_ShouldReturnException() {
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        Vehicle vehicle3 = new Vehicle("Red","Swift","MH20 TY 01210");
        Vehicle vehicle4 = new Vehicle("Wine","Honda","MH19 BO 4920");
        Vehicle vehicle5 = new Vehicle("Black","Volvo","MH24 AE 84235");
        Vehicle vehicle6 = new Vehicle("Blue","Suzuki","MH17 UH 32541");
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkedVehicle(vehicle2, DriverType.NORMAL, VehicleType.LARGE);
            parkingLotSystem.parkedVehicle(vehicle4, DriverType.NORMAL, VehicleType.SMALL);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.DRIVER_VEHICLE_TYPE, "SMALL", "HANDICAP");
        }catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC17
    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnDetails() {
        List<String> expectedList=new ArrayList<>();
        Vehicle vehicle2 = new Vehicle("White","BMW","MH18 BN 78963");
        Vehicle vehicle3 = new Vehicle("Red","Swift","MH20 TY 01210");
        Vehicle vehicle4 = new Vehicle("Wine","Honda","MH19 BO 4920");
        Vehicle vehicle5 = new Vehicle("Black","Volvo","MH24 AE 84235");

        parkingLot.setCapacity(10);
        expectedList.add("0 Toyota White MH19 AB 2341");
        expectedList.add("1 BMW White MH18 BN 78963");
        expectedList.add("2 Swift Red MH20 TY 01210");
        expectedList.add("3 Honda Wine MH19 BO 4920");
        expectedList.add("4 Volvo Black MH24 AE 84235");

        parkingLotSystem.parkedVehicle(vehicle,DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle2,DriverType.NORMAL,VehicleType.LARGE);
        parkingLotSystem.parkedVehicle(vehicle3,DriverType.HANDICAP,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle4,DriverType.NORMAL,VehicleType.SMALL);
        parkingLotSystem.parkedVehicle(vehicle5,DriverType.HANDICAP,VehicleType.SMALL);
        List<String> vehiclesDetails = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.ALL_PARKED);
        Assert.assertEquals(expectedList,vehiclesDetails);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParked_ShouldReturnException() {
        try{
            parkingLotSystem.parkedVehicle(vehicle, DriverType.HANDICAP, VehicleType.SMALL);
            parkingLotSystem.parkedVehicle(vehicle1, DriverType.HANDICAP, VehicleType.SMALL);
            parkingLotSystem.unParkedVehicle(vehicle);
            parkingLotSystem.unParkedVehicle(vehicle1);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortField.ALL_PARKED);
        }catch (ParkingLotException e)
        {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

}