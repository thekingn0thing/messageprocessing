package com.github.thekingnothing.messageprocessing.mock;

import com.github.thekingnothing.messageprocessing.reporting.Report;

import java.util.Collections;
import java.util.List;

public class StringReport implements Report {
    private final String report;
    
    public StringReport(final String report) {
        this.report = report;
    }
    
    @Override
    public List<String> lines() {
        return Collections.singletonList(report);
    }
}
