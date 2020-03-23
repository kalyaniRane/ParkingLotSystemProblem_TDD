package com.bridgelabz.parkinglot;

import java.time.LocalTime;

public class Vehicle {


    public LocalTime time;
    public int slot;

    public void setSlot(int slot)
    {
        this.slot=slot;
    }

    public void setTime(){
        this.time=LocalTime.now();
    }

    public LocalTime getTime() {
        return time;
    }

    public int getSlot() {
        return slot;
    }
}
