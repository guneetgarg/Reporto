package com.test.sample;


import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class FunctionalSample1 {

    @Test(description = "Launches the WordPress site")
    public void TestA() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void TestB() {
        Assert.assertEquals("2", "4", "expected 3 but got 4");
    }


    @Test
    public void TestC() {

    }

    @Test
    public void TestD() {
        Assert.fail();
    }

    @Test
    public void TestE() {
    }

    @Test
    public void testCaseSkipException() {
        System.out.println("Im in skip exception");
        throw new SkipException("Skipping this exception");
    }


}
