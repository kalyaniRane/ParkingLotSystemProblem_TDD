package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.Dao.ParkingSlot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

public enum VehicleSortField {

    COLOUR,NAME_COLOUR, NAME, TIME_IN_MINUTES, DRIVER_VEHICLE_TYPE, ALL_PARKED;

    public static Map<VehicleSortField, IntPredicate> sortFieldPredicateMap=new HashMap<>();
   public static List<ParkingSlot> vehicle=new ArrayList<>();
    public void initializeList(List<ParkingSlot>vehicles)
    {
        vehicle=vehicles;
    }
    public static Map<VehicleSortField, IntPredicate> SortVehicleList(String...field){
        sortFieldPredicateMap.put(VehicleSortField.COLOUR, slot->vehicle.get(slot).getVehicle().getColour().equals(field[0]));
        sortFieldPredicateMap.put(VehicleSortField.NAME_COLOUR, slot->vehicle.get(slot).getVehicle().getVehicleName().equals(field[0]) && vehicle.get(slot).getVehicle().getColour().equals(field[1]));
        sortFieldPredicateMap.put(VehicleSortField.NAME, slot->vehicle.get(slot).getVehicle().getVehicleName().equals(field[0]));
        sortFieldPredicateMap.put(VehicleSortField.TIME_IN_MINUTES, slot->vehicle.get(slot).getTime().getMinute()-LocalTime.now().getMinute()<=Integer.parseInt(field[0]));
        sortFieldPredicateMap.put(VehicleSortField.DRIVER_VEHICLE_TYPE, slot-> vehicle.get(slot).getVehicleType1().equals(VehicleType.valueOf(field[0])) && vehicle.get(slot).getDriver().equals(DriverType.valueOf(field[1])));
        sortFieldPredicateMap.put(VehicleSortField.ALL_PARKED, slot-> vehicle.get(slot)!=null);
        return sortFieldPredicateMap;
    }

}
