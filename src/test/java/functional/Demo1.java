package functional;


import Util.Wrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FilenameUtils;
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
        String value = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment");
        System.out.println(value);
        if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment1") == null) {
            System.out.println("hello");
        }

        File folder = new File("C:\\Users\\guneet.garg\\IdeaProjects\\Reporto\\src\\main\\resources\\configuration");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                System.out.println(FilenameUtils.getExtension(listOfFiles[i].getName()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        new Wrapper().convertToJson(null);
    }
}
