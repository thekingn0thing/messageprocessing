package com.github.thekingnothing.messageprocessing.report.support;

import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.report.Report;
import com.github.thekingnothing.messageprocessing.report.ReportFactory;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.Map.Entry;

public class OrderReportFactory implements ReportFactory {
    @Override
    public Report createReport(final Storage storage) {
        final StringLineReport report = new StringLineReport();
    
        for (Entry<String, Total> entry : storage.allTotals().entrySet()) {
            final Total total = entry.getValue();
            report.append(entry.getKey() + ": Quantity " + total.getQuantity() + ". Price " + total.getTotalPrice());
        }
        
        return report;
    }
}
