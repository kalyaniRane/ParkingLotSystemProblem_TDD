package com.bridgelabz.parkinglot.enums;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import java.util.ArrayList;
import java.util.Collections;

public enum VehicleType {

    LARGE{

        @Override
        public int getEmptySlot(ArrayList<Integer> emptySlotList) {
            Collections.sort(emptySlotList);
            for (int slot=0;slot<emptySlotList.size();slot++){
                if(emptySlotList.get(slot)==slot && emptySlotList.get(slot+1)==slot+1 && emptySlotList.get(slot+2)==slot+2)
                    return emptySlotList.get(slot+1);
            }
            if(!emptySlotList.isEmpty())
                return emptySlotList.get(0);
            throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
        }

    },
    SMALL{

        @Override
        public int getEmptySlot(ArrayList<Integer> emptySlotList) {

            if(!emptySlotList.isEmpty())
                return emptySlotList.get(0);
            throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
        }

    };

    public abstract int getEmptySlot(ArrayList<Integer> parkingLots);
}
