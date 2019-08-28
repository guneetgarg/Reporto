package com.reporto;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class ReportoListner implements IReporter, ITestListener {


    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        System.out.println("*********************************");
        System.out.println(xmlSuites.size());

        System.out.println(suites.get(0).getHost());


        for (ISuite s : suites) {
            Map<String, ISuiteResult> suiteResults = s.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext testContext = sr.getTestContext();
                System.out.println(testContext.getPassedTests().size());
                System.out.println(testContext.getFailedTests().size());
                System.out.println(testContext.getSkippedTests().size());
            }
        }


    }
}
