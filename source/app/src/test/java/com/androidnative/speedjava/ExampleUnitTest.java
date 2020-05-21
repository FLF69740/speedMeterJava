package com.androidnative.speedjava;

import com.androidnative.speedjava.business.CAverage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private CAverage getCalculator(){ return new CAverage(); }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testIfCalculatorGiveGoodAverage(){

        CAverage calculator = getCalculator();

        assertEquals(0, calculator.getAverage(), 1);

        short v1 = 10;
        calculator.setValue(v1);

        assertEquals(1, calculator.getCounter());
        assertEquals(10, calculator.getAverage(), 1);

        short v2 = 2;
        calculator.setValue(v2);

        assertEquals(2, calculator.getCounter());
        assertEquals(6, calculator.getAverage(), 1);

    }
}