package com.example.foreverfind;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class IT19503806 {

    static PaymentSummary paymentSummary;
    private int result,result2,result3,result4;

    @BeforeClass
    public static void CreateObject(){

        paymentSummary = new PaymentSummary();
    }

    @Before
    public void getAmount(){

        result = (int) paymentSummary.calculateDisTotal(2000,300,200);
        result2 =  (int) paymentSummary.calculateDisTotal(200,800,300);
        result3 =  (int) paymentSummary.calculateDisTotal(3000,0,100);
        result4 =  (int) paymentSummary.calculateDisTotal(1500,0,0);
    }

    @Test
    public void testTotalAmount(){

        assertEquals(1800,result);

        assertEquals(0,result2);

        assertEquals(2900,result3);

        assertEquals(1500,result4);
    }

    @After
    public void clearData(){
        result=0;
        result2=0;
        result3=0;
        result4=0;
    }

    @AfterClass
    public static void deleteObject(){
        paymentSummary =null;
    }


}
