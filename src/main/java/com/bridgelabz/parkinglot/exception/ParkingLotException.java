package com.bridgelabz.parkinglot.exception;

public class ParkingLotException extends RuntimeException {

    private String message;

    public enum ExceptionType{
        VEHICLE_NOT_FOUND,LOT_IS_FULL,VEHICLE_ALREADY_PARKED,TIME_NOT_AVAILABLE
    }
    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType exceptionType) {
        this.message=message;
        this.type=exceptionType;
    }

}
