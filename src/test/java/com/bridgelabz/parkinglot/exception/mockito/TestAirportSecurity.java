package com.bridgelabz.parkinglot.exception.mockito;

import com.bridgelabz.parkinglot.AirportSecurity;
import com.bridgelabz.parkinglot.ParkingLotInformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestAirportSecurity {

    AirportSecurity airportSecurity;
    Object vehicle;
    ParkingLotInformer lotInformer;

    @Before
    public void setUp() throws Exception {
        vehicle=new Object();
        airportSecurity = new AirportSecurity();
        lotInformer=mock(ParkingLotInformer.class);
    }


    @Test
    public void givenMockitoTest_WhenCheckSecurityClassWithCheckCapacityIsFull() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                airportSecurity.capacityIsFull();
                return null;
            }
        }).when(lotInformer).getInformedObserver();

        lotInformer.getInformedObserver();
        Assert.assertTrue(airportSecurity.isCapacityFull());
    }

}
