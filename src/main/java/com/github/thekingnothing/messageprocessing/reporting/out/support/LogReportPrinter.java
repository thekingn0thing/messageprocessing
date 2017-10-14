package com.github.thekingnothing.messageprocessing.reporting.out.support;

import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.reporting.Report;
import com.github.thekingnothing.messageprocessing.reporting.out.ReportPrinter;

public class LogReportPrinter implements ReportPrinter {
    private final Logger logger;
    
    public LogReportPrinter(final Logger logger) {
        this.logger = logger;
    }
    
    @Override
    public void print(final Report report) {
        for (String line : report.lines()) {
            logger.info(line);
        }
    }
}
