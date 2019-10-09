package com.reporto;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.reporters.Files;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ReportoListener extends TestListenerAdapter implements IReporter, ITestListener {
    String passHtml = "";
    String skipHtml = "";
    String failHtml = "";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        Date startTime = null;
        Date endTime = null;
        StatsResult.automationSuiteName = xmlSuites.get(0).getName();
        StatsResult.parallelStatus = "" + xmlSuites.get(0).getParallel().isParallel();

        System.out.println(StatsResult.automationSuiteName);
        System.out.println(StatsResult.parallelStatus);

        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();

            for (String testName : suiteResults.keySet()) {

                ISuiteResult suiteResult = suiteResults.get(testName);
                ITestContext testContext = suiteResult.getTestContext();

                System.out.println("Suite Start Time" + testContext.getStartDate());
                System.out.println("Suite End Time" + testContext.getEndDate());
                startTime = testContext.getStartDate();
                endTime = testContext.getEndDate();

                passedTestCase(testContext.getPassedTests().getAllResults(), "pass");
                passedTestCase(testContext.getFailedTests().getAllResults(), "fail");
                passedTestCase(testContext.getSkippedTests().getAllResults(), "skip");
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

        fileContent = fileContent.replace("passedData", String.valueOf(StatsResult.passed))
                .replace("totalData", String.valueOf(StatsResult.getTotal()))
                .replace("failedData", String.valueOf(StatsResult.failed))
                .replace("skippedData", String.valueOf(StatsResult.skipped));

        fileContent = fileContent.replace("osData", System.getProperty("os.name"))
                .replace("userData", System.getProperty("user.name"))
                .replace("startTimeData", startTime.toString())
                .replace("endTimeData", endTime.toString())
                .replace("javaVersionnData", System.getProperty("java.version"));


        fileContent = fileContent.replace("passedPerData", String.format("%.02f", (StatsResult.passed * 100.00 / StatsResult.getTotal())))
                .replace("failedPerData", String.format("%.02f", (StatsResult.failed * 100.00 / StatsResult.getTotal())))
                .replace("skippedPerData", String.format("%.02f", (StatsResult.skipped * 100.00 / StatsResult.getTotal())));


        fileContent = fileContent.replace("passTestCaseAdd", passHtml)
                .replace("skipTestCaseAdd", skipHtml)
                .replace("failTestCaseAdd", failHtml);


        try {
            Files.writeFile(fileContent, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("TestNG Metrics " + file1 + " is created successfully");

    }

    public void passedTestCase(Set<ITestResult> iTestResults, String type) {

        if (iTestResults.size() > 0) {
            for (ITestResult testResult : iTestResults) {
                System.out.println("**************** " + type + " ********************************");

                if (type == "pass") {
                    passHtml += passTestCaseEmbeded();
                } else if (type == "skip") {
                    skipHtml += skipTestCaseEmbeded();
                } else if (type == "fail") {
                    failHtml += failTestCaseEmbeded();
                }

                DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
                Date result = new Date(testResult.getStartMillis());
                System.out.println("Date " + simple.format(result));

                String last[] = testResult.getInstanceName().split("\\.");
                System.out.println("Class Name " + last[last.length - 1]);
                System.out.println("Test Case Name " + testResult.getName());

                String testcasename = testResult.getName();
                String classname = last[last.length - 1];


                String packageName = "";
                int i = 0;
                while (last.length - 1 > i) {
                    packageName += last[i];
                    i++;
                    if (last.length - 1 > i) {
                        packageName += ".";
                    }
                }

                String packagename = packageName;

                System.out.println("Package Name " + packageName);
                if (testResult.getMethod().getDescription() != null) {
                    System.out.println("Description " + testResult.getMethod().getDescription());
                }


                if (type == "pass") {
                    passHtml = passHtml.replace("testcasename", testcasename);
                    passHtml = passHtml.replace("classname", classname);
                    passHtml = passHtml.replace("packagename", packagename);
                } else if (type == "skip") {
                    skipHtml = skipHtml.replace("testcasename", testcasename);
                    skipHtml = skipHtml.replace("classname", classname);
                    skipHtml = skipHtml.replace("packagename", packagename);
                    skipHtml = skipHtml.replace("errormessage", testResult.getThrowable().toString());
                } else if (type == "fail") {
                    failHtml = failHtml.replace("testcasename", testcasename);
                    failHtml = failHtml.replace("classname", classname);
                    failHtml = failHtml.replace("packagename", packagename);
                    failHtml = failHtml.replace("errormessage", testResult.getThrowable().toString());
                }
            }
        }
    }


    public String passTestCaseEmbeded() {
        String pass = "        <div id=\"divpad\" data-type=\"accordion-section\" data-filter=\"passType\">\n" +
                "                            <div id=\"divPassed\" data-type=\"accordion-section-title\">testcasename</div>\n" +
                "                            <div class=\"accordion-content\" data-type=\"accordion-section-body\">\n" +
                "                                <div class=\"table-responsive\">\n" +
                "                                    <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n" +
                "                                        <tr>\n" +
                "                                            <td>Class Name</td>\n" +
                "                                            <td>classname</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Test Case Name</td>\n" +
                "                                            <td>testcasename</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Package Name</td>\n" +
                "                                            <td>packagename</td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>";

        return pass;
    }

    public String skipTestCaseEmbeded() {

        return "                        <div id=\"divpad\" data-type=\"accordion-section\" data-filter=\"skipType\">\n" +
                "                            <div id=\"divSkipped\" data-type=\"accordion-section-title\">testcasename</div>\n" +
                "                            <div class=\"accordion-content\" data-type=\"accordion-section-body\">\n" +
                "\n" +
                "                                <div class=\"table-responsive\">\n" +
                "                                    <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n" +
                "                                           <tr>\n" +
                "                                            <td>Class Name</td>\n" +
                "                                            <td>classname</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Test Case Name</td>\n" +
                "                                            <td>testcasename</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Package Name</td>\n" +
                "                                            <td>packagename</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Error Message</td>\n" +
                "                                            <td>errormessage</td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </div>\n" +
                "\n" +
                "                            </div>\n" +
                "                        </div>\n";
    }

    public String failTestCaseEmbeded() {
        return " <div id=\"divpad\" data-type=\"accordion-section\" data-filter=\"failType\">\n" +
                "                            <div id=\"divFailed\" data-type=\"accordion-section-title\">testcasename</div>\n" +
                "                            <div class=\"accordion-content\" data-type=\"accordion-section-body\">\n" +
                "                                <div class=\"table-responsive\">\n" +
                "                                    <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\n" +
                "                                         <tr>\n" +
                "                                            <td>Class Name</td>\n" +
                "                                            <td>classname</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Test Case Name</td>\n" +
                "                                            <td>testcasename</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Package Name</td>\n" +
                "                                            <td>packagename</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td>Error Message</td>\n" +
                "                                            <td>errormessage</td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>";
    }

}