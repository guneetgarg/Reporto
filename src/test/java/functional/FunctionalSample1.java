package functional;


import org.testng.Assert;
import org.testng.annotations.Test;

public class FunctionalSample1 {

    @Test(description="Launches the WordPress site")
    public void TestA() {

    }

    @Test
    public void TestB() {
        Assert.fail();
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
}
