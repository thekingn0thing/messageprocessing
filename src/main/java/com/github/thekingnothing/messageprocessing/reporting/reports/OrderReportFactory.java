package com.github.thekingnothing.messageprocessing.reporting.reports;

import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.reporting.Report;
import com.github.thekingnothing.messageprocessing.reporting.ReportFactory;
import com.github.thekingnothing.messageprocessing.reporting.support.StringLineReport;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.Map.Entry;

public class OrderReportFactory implements ReportFactory {
    @Override
    public Report createReport(final Storage storage) {
        final StringLineReport report = new StringLineReport();
    
        report.append("| Symbol | Quantity | Total Value |");
        for (Entry<String, Total> entry : storage.allTotals().entrySet()) {
            final Total total = entry.getValue();
            report.append("| " + entry.getKey() + " | " + total.getQuantity() + " | " + total.getTotalValue() + " |");
        }
        
        return report;
    }
}
