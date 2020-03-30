package com.bridgelabz.parkinglot.com.exception.mockito;

import com.bridgelabz.parkinglot.ParkingLot;
import com.bridgelabz.parkinglot.ParkingLotInformer;
import com.bridgelabz.parkinglot.ParkingLotOwner;
import com.bridgelabz.parkinglot.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;


public class TestParkingOwner {

    Object vehicle;
    ParkingLotOwner owner;
    ParkingLotInformer lotInformer;

    @Before
    public void setUp() throws Exception {
        vehicle=new Object();
        owner=new ParkingLotOwner();
        lotInformer=mock(ParkingLotInformer.class);
    }

    @Test
    public void givenMockitoTest_WhenCheckOwnerClassWithCheckCapacityIsFull() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                owner.capacityIsFull();
                return null;
            }
        }).when(lotInformer).getInformedObserver();

        lotInformer.getInformedObserver();
        Assert.assertTrue(owner.isCapacityFull());
    }

}
