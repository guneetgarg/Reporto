package com.reporto;

import com.sun.org.glassfish.external.statistics.Stats;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class ReportoListener implements IReporter, ITestListener {


    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {


        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();
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
        System.out.println("passed " + StatsResult.passed);
        System.out.println("failed " + StatsResult.failed);
        System.out.println("skipped " + StatsResult.skipped);
        System.out.println("ignored " + StatsResult.ignored);
        System.out.println(StatsResult.getTotal());

    }
}
