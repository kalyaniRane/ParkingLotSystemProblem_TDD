package com.bridgelabz.parkinglot.com.exception;

public class ParkingLotException extends RuntimeException {

    private String message;

    public enum ExceptionType{
        VEHICLE_NOT_FOUND,LOT_IS_FULL,VEHICLE_ALREADY_PARKED,
    }
    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType exceptionType) {
        this.message=message;
        this.type=exceptionType;
    }

}
