package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.ParkingLotSystem;
import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.Collections;

public enum DriverType {

    NORMAL{
        @Override
        public ArrayList<Integer> getEmptyList(ArrayList<Integer> emptySlot) {
            Collections.sort(emptySlot, Collections.reverseOrder());
            return emptySlot;
        }},
    HANDICAP{
        @Override
        public ArrayList<Integer> getEmptyList(ArrayList<Integer> emptySlot){
            Collections.sort(emptySlot);
            return emptySlot;
        }

    };

    public abstract ArrayList<Integer>getEmptyList(ArrayList<Integer> emptySlot);
}
