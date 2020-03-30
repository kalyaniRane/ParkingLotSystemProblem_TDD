package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotInformer {

    private static List<ParkingLotObserver> observers=new ArrayList<>();

    //Function to register Observers in List For ParkingLot
    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void getInformedObserver()
    {
        for(ParkingLotObserver observer : observers)
            observer.capacityIsFull();
    }

}
