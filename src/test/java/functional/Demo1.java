package functional;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Demo1 {

    @Test
    public void main() {
        System.out.println("Property-property :" + System.getProperty("env"));
        String value = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("sUsername");
        System.out.println(value);


    }
}
