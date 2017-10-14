package com.github.thekingnothing.messageprocessing.processing.processor;

import com.github.thekingnothing.messageprocessing.mock.StringReport;
import com.github.thekingnothing.messageprocessing.processing.PostProcessor;
import com.github.thekingnothing.messageprocessing.reporting.Report;
import com.github.thekingnothing.messageprocessing.reporting.ReportFactory;
import com.github.thekingnothing.messageprocessing.reporting.out.ReportPrinter;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportPostProcessorTest {
    
    private int reportPeriod;
    private Storage storage;
    private PostProcessor objectUnderTest;
    private MockReportFactory mockReportFactory;
    private MockReportPrinter reportPrinter;
    
    @Before
    public void setUp() throws Exception {
        reportPeriod = 2;
        
        storage = new DefaultStorage();
        
        mockReportFactory = new MockReportFactory();
        reportPrinter = new MockReportPrinter();
        objectUnderTest = new ReportPostProcessor(reportPeriod, mockReportFactory, reportPrinter);
    }
    
    @Test
    public void should_create_a_report_and_log_for_n_messages() {
        final String text = "text";
        mockReportFactory.setReport(new StringReport(text));
        
        for (int i = 0; i < reportPeriod; i++) {
            objectUnderTest.afterMessage(storage);
        }
        
        reportPrinter.assertPrinted(text);
    }
    
    private static class MockReportFactory implements ReportFactory {
        private Report report;
        
        private void setReport(final Report report) {
            this.report = report;
        }
        
        @Override
        public Report createReport(final Storage storage) {
            return report;
        }
    }
    
    private static class MockReportPrinter implements ReportPrinter {
        
        private final List<String> data;
        
        private MockReportPrinter() {
            data = new ArrayList<>();
        }
        
        @Override
        public void print(final Report report) {
            data.addAll(report.lines());
        }
        
        private void assertPrinted(final String text) {
            assertThat(data)
                .as("Assert that report with text `%s` has been print", text)
                .contains(text);
        }
    }
}
