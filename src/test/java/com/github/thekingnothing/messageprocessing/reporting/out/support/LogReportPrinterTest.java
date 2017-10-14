package com.github.thekingnothing.messageprocessing.reporting.out.support;


import com.github.thekingnothing.messageprocessing.mock.MockLogger;
import com.github.thekingnothing.messageprocessing.reporting.out.support.LogReportPrinter;
import com.github.thekingnothing.messageprocessing.reporting.support.StringLineReport;
import org.junit.Test;

public class LogReportPrinterTest {
    
    @Test
    public void should_print_report_to_log() {
        final String reportText1 = "aaaaa";
        final String reportText2 = "bbbbb";
        
        final MockLogger logger = new MockLogger();
        
        final StringLineReport report = new StringLineReport();
        report.append(reportText1);
        report.append(reportText2);
        
        final LogReportPrinter objectUnderTest = new LogReportPrinter(logger);
        objectUnderTest.print(report);
        
        logger.assertLogContains(reportText1);
        logger.assertLogContains(reportText2);
    }
    
}