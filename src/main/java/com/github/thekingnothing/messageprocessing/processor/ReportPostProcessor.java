package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.PostProcessor;
import com.github.thekingnothing.messageprocessing.report.Report;
import com.github.thekingnothing.messageprocessing.report.ReportFactory;
import com.github.thekingnothing.messageprocessing.report.ReportPrinter;
import com.github.thekingnothing.messageprocessing.storage.Storage;

public class ReportPostProcessor implements PostProcessor {
    
    private final int reportPeriod;
    private final ReportFactory reportFactory;
    private final ReportPrinter reportPrinter;
    
    private int counter;
    
    public ReportPostProcessor(final int reportPeriod, final ReportFactory reportFactory,
                               final ReportPrinter reportPrinter) {
        this.reportPeriod = reportPeriod;
        this.reportFactory = reportFactory;
        this.reportPrinter = reportPrinter;
        counter = 0;
    }
    
    @Override
    public void afterMessage(final Storage storage) {
        incrementCounter();
        if (timeForReport()) {
            logReport(storage);
        }
    }
    
    private void logReport(final Storage storage) {
        final Report report = reportFactory.createReport(storage);
        reportPrinter.print(report);
    }
    
    private boolean timeForReport() {
        return counter % reportPeriod == 0;
    }
    
    private void incrementCounter() {
        ++counter;
    }
}
