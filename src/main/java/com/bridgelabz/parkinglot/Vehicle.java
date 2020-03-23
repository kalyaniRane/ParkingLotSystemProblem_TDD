package com.bridgelabz.parkinglot;

import java.time.LocalTime;

public class Vehicle {


    public LocalTime time;

    public void setTime(){
        this.time=LocalTime.now();
    }

    public LocalTime getTime() {
        return time;
    }

}
