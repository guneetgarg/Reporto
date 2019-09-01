package com.reporto;

import org.testng.*;
import org.testng.annotations.Test;
import org.testng.internal.TestNGMethod;
import org.testng.reporters.Files;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReportoListener implements IReporter {


    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

      //  System.out.println(xmlSuites.get(0).getParallel().toString());


        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();

            for (String testName : suiteResults.keySet()) {

                ISuiteResult suiteResult = suiteResults.get(testName);
                ITestContext testContext = suiteResult.getTestContext();

                //  System.out.println(testContext.getStartDate());
                // System.out.println(testContext.getEndDate());


                IResultMap passResult = testContext.getPassedTests();

                Set<ITestResult> testsPassed = passResult.getAllResults();
                //  System.out.println(testContext.getPassedTests().size());
                //  System.out.println(testContext.getFailedTests().size());
                //  System.out.println(testContext.getSkippedTests().size());

                if (testsPassed.size() > 0) {
                    for (ITestResult testResult : testsPassed) {
                        //System.out.println(testResult.getStartMillis());
 //                       System.out.print(testResult.getInstanceName());
                        System.out.println(testResult.getName());
                        if (testResult.getMethod().getDescription() != null) {
                            //     System.out.println("<-" + testResult.getMethod().getDescription());
                        } else {
                            //   System.out.println("^^^^^^^^^^^^^^^^");
                        }
                    }
                }


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
        File file = new File("C:\\Users\\guneet.garg\\IdeaProjects\\Reporto\\src\\main\\resources\\startbootstrap-sb-admin-2-gh-pages\\index.html");
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
}