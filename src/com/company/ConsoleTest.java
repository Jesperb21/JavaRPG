package com.company;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ConsoleTest {

    @Test
    public void testRandomInt(){
        Random r = new Random();
        int num1 = r.nextInt(10)+1;
        int num2 = r.nextInt(10) + num1;

        double result = Console.RandomInt(num1, num2);
        assertTrue("error! randomly generated value was too high", result <= num2);
        assertTrue("error! randomly generated value was too low", result >= num1);
    }

    @Test
    public void testRandomDouble(){
        Random r = new Random();
        double num1 = r.nextDouble();
        double num2 = r.nextDouble() + num1;

        double result = Console.RandomDouble(num1, num2);
        assertTrue("error! randomly generated value was too high", result <= num2);
        assertTrue("error! randomly generated value was too low", result >= num1);
    }
}