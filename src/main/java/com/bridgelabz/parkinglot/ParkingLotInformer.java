package com.bridgelabz.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotInformer {

    List<ParkingLotObserver> observers=new ArrayList<>();
    static ParkingLotInformer instance;

    public static ParkingLotInformer getInstance(){
        if(instance == null)
            instance = new ParkingLotInformer();
        return instance;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void getInformedObserver()
    {
        for(ParkingLotObserver observer : observers)
            observer.capacityIsFull();
    }

}
