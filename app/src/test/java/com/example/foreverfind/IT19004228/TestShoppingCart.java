package com.example.foreverfind.IT19004228;

import com.example.foreverfind.shoppingCart;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestShoppingCart {

    static shoppingCart sc;
    int result;
    int result2;
    int result3;
    int result4;



    @BeforeClass
    public static void CreateObj(){
        sc = new shoppingCart();
    }

    @Before
    public void getValues(){
       result =  sc.calOnePrice(2000,4);
       result2 = sc.calOverallTotalPrice(8000);
       result3 = sc.calOnePrice(1500,3);
       result4 = sc.calOnePrice(3000,2);


    }
    @Test
    public void TestOneItemTypeTotal(){
        assertEquals(8000,result);
        assertEquals(4500,result3);
        assertEquals(6000,result4);
    }
    @Test
    public void TestOverallPrice()
    {
        assertEquals(8000,result2);

    }
    @After
    public void clearDate(){
        result=0;
        result2=0;
        result3 =0;
        result4=0;

    }
    @AfterClass
    public static void deleteObj(){
        sc = null;
    }

}
