package com.reporto;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.reporters.Files;
import org.testng.xml.XmlSuite;
import sun.rmi.rmic.iiop.Generator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class ReportoListener extends TestListenerAdapter implements IReporter, ITestListener {

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        StatsResult.automationSuiteName = xmlSuites.get(0).getName();
        StatsResult.parallelStatus = "" + xmlSuites.get(0).getParallel().isParallel();

        System.out.println(StatsResult.automationSuiteName);
        System.out.println(StatsResult.parallelStatus);

        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();

            for (String testName : suiteResults.keySet()) {

                ISuiteResult suiteResult = suiteResults.get(testName);
                ITestContext testContext = suiteResult.getTestContext();

                //  System.out.println(testContext.getStartDate());
                // System.out.println(testContext.getEndDate());


                passedTestCase(testContext);

                failedTestCase(testContext);
                skipedTestCase(testContext);
            }


            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext testContext = sr.getTestContext();

                StatsResult.passed += testContext.getPassedTests().size();
                StatsResult.failed += testContext.getFailedTests().size();
                int retriedPerTest = 0;
                int skippedPerTest = 0;
                for (ITestResult result : testContext.getSkippedTests().getAllResults()) {
                    if (result.wasRetried()) {
                        retriedPerTest++;
                    } else {
                        skippedPerTest++;
                    }
                }
                StatsResult.skipped += skippedPerTest;
                StatsResult.retried += retriedPerTest;
                StatsResult.ignored += testContext.getExcludedMethods().size();

//                System.out.println("Testng Test Name " + testContext.getName());//<test name="testTest">


            }
        }

        String fileName = "Metrics-" + new SimpleDateFormat("yyyyMMMdd-HHmm'.html'").format(new Date());
        String tempFile = outputDirectory + File.separator + fileName;
        File file1 = new File(tempFile);

        byte[] data1 = new byte[0];
        //File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\startbootstrap-sb-admin-2-gh-pages\\index.html");
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/startbootstrap-sb-admin-2-gh-pages/index.html");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            data1 = new byte[(int) file.length()];
            fis.read(data1);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileContent = null;
        try {
            fileContent = new String(data1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        fileContent = fileContent.replace("passedData", String.valueOf(StatsResult.passed));
        fileContent = fileContent.replace("totalData", String.valueOf(StatsResult.getTotal()));
        fileContent = fileContent.replace("failedData", String.valueOf(StatsResult.failed));
        fileContent = fileContent.replace("skippedData", String.valueOf(StatsResult.skipped));

        fileContent = fileContent.replace("osData", System.getProperty("os.name"));
        fileContent = fileContent.replace("userData", System.getProperty("user.name"));
        fileContent = fileContent.replace("startTimeData", System.getProperty("os.name"));
        fileContent = fileContent.replace("endTimeData", System.getProperty("java.version"));
        fileContent = fileContent.replace("javaVersionnData", System.getProperty("java.version"));

        fileContent = fileContent.replace("passedPerData", String.format("%.02f", (StatsResult.passed * 100.00 / StatsResult.getTotal())));
        fileContent = fileContent.replace("failedPerData", String.format("%.02f", (StatsResult.failed * 100.00 / StatsResult.getTotal())));
        fileContent = fileContent.replace("skippedPerData", String.format("%.02f", (StatsResult.skipped * 100.00 / StatsResult.getTotal())));

        try {
            Files.writeFile(fileContent, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("TestNG Metrics " + file1 + " is created successfully");


    }

    public void passedTestCase(ITestContext testContext) {
        IResultMap passResult = testContext.getPassedTests();

        Set<ITestResult> testsPassed = passResult.getAllResults();
        if (testsPassed.size() > 0) {
            for (ITestResult testResult : testsPassed) {
                System.out.println("****************PASSED********************************");
                //System.out.println(testResult.getStartMillis());
                String last[] = testResult.getInstanceName().split("\\.");
                System.out.println("Class Name " + last[last.length - 1]);
                System.out.println("Test Case Name " + testResult.getName());
                String packageName = "";
                int i = 0;
                while (last.length - 1 > i) {
                    packageName += last[i];
                    i++;
                    if (last.length - 1 > i) {
                        packageName += ".";
                    }
                }
                System.out.println("Package Name " + packageName);
                if (testResult.getMethod().getDescription() != null) {
                    System.out.println("<-" + testResult.getMethod().getDescription());
                } else {
                    System.out.println("^^^^^^^^^^^^^^^^");
                }
            }
        }
    }


    public void failedTestCase(ITestContext testContext) {
        IResultMap failedResult = testContext.getFailedTests();

        Set<ITestResult> testsFailed = failedResult.getAllResults();
        if (testsFailed.size() > 0) {
            for (ITestResult testResult : testsFailed) {
                System.out.println("****************FAILED********************************");
                //System.out.println(testResult.getStartMillis());
                String last[] = testResult.getInstanceName().split("\\.");
                System.out.println("Class Name " + last[last.length - 1]);
                System.out.println("Test Case Name " + testResult.getName());

                System.out.println("Error " + testResult.getThrowable());
                System.out.println("Error " + testResult.getThrowable().getStackTrace().toString());
                String packageName = "";
                int i = 0;
                while (last.length - 1 > i) {
                    packageName += last[i];
                    i++;
                    if (last.length - 1 > i) {
                        packageName += ".";
                    }
                }
                System.out.println("Package Name " + packageName);
                if (testResult.getMethod().getDescription() != null) {
                    System.out.println("<-" + testResult.getMethod().getDescription());
                } else {
                    System.out.println("^^^^^^^^^^^^^^^^");
                }
            }
        }
    }


    public void skipedTestCase(ITestContext testContext) {
        IResultMap skipedResult = testContext.getSkippedTests();

        Set<ITestResult> testsSkipped = skipedResult.getAllResults();
        if (testsSkipped.size() > 0) {
            for (ITestResult testResult : testsSkipped) {
                System.out.println("*************SKIPPED***********************************");
                //System.out.println(testResult.getStartMillis());
                String last[] = testResult.getInstanceName().split("\\.");
                System.out.println("Class Name " + last[last.length - 1]);
                System.out.println("Test Case Name " + testResult.getName());

                System.out.println("Error " + testResult.getThrowable());
                System.out.println("Error " + testResult.getThrowable().getStackTrace().toString());
                String packageName = "";
                int i = 0;
                while (last.length - 1 > i) {
                    packageName += last[i];
                    i++;
                    if (last.length - 1 > i) {
                        packageName += ".";
                    }
                }
                System.out.println("Package Name " + packageName);
                if (testResult.getMethod().getDescription() != null) {
                    System.out.println("<-" + testResult.getMethod().getDescription());
                } else {
                    System.out.println("^^^^^^^^^^^^^^^^");
                }
            }
        }
    }


    String filePath = "D:\\SCREENSHOTS";

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("***** Error " + result.getName() + " test has failed *****");
        String methodName = result.getName().toString().trim();
        ITestContext context = result.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        try {
            takeScreenShot(methodName, driver);
        } catch (AWTException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenShot(String methodName, WebDriver driver) throws AWTException, IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(filePath + methodName + ".png"));
            System.out.println("***Placed screen shot in " + filePath + " ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}