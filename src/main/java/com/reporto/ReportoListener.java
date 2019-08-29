package com.reporto;

import org.testng.*;
import org.testng.annotations.Test;
import org.testng.internal.TestNGMethod;
import org.testng.xml.XmlSuite;

import java.util.*;

public class ReportoListener implements IReporter {


    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();

            for (String testName : suiteResults.keySet()) {


                ISuiteResult suiteResult = suiteResults.get(testName);

                ITestContext testContext = suiteResult.getTestContext();
                IResultMap passResult = testContext.getPassedTests();

                Set<ITestResult> testsPassed = passResult.getAllResults();
                System.out.println(testContext.getPassedTests().size());
                System.out.println(testContext.getFailedTests().size());
                System.out.println(testContext.getSkippedTests().size());

                if (testsPassed.size() > 0) {
                    for (ITestResult testResult : testsPassed) {
                        System.out.println(testResult.getStartMillis());
                        System.out.println(testResult.getInstanceName());
                        System.out.println(testResult.getName());
                        if (testResult.getMethod().getDescription() != null)
                            System.out.println("<-" + testResult.getMethod().getDescription());
                        else {
                            System.out.println("^^^^^^^^^^^^^^^^");
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

                System.out.println("Testng Test Name " + testContext.getName());//<test name="testTest">


            }
        }
        System.out.println("passed " + StatsResult.passed);
        System.out.println("failed " + StatsResult.failed);
        System.out.println("skipped " + StatsResult.skipped);
        System.out.println("ignored " + StatsResult.ignored);
        System.out.println("total " + StatsResult.getTotal());
    }
}