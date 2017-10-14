package com.github.thekingnothing.messageprocessing.reporting.reports;

import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.SymbolAdjustment;
import com.github.thekingnothing.messageprocessing.reporting.Report;
import com.github.thekingnothing.messageprocessing.reporting.ReportFactory;
import com.github.thekingnothing.messageprocessing.reporting.support.StringLineReport;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class AdjustmentReportFactory implements ReportFactory {
    @Override
    public Report createReport(final Storage storage) {
        final StringLineReport report = new StringLineReport();
        
        for (SymbolAdjustment symbolAdjustment : storage.allAdjustment()) {
            String line = "Adjustments for ";
            line += symbolAdjustment.getSymbol();
            line += ": ";
            line += adjustments(symbolAdjustment.getAdjustments());
            
            report.append(line);
        }
        
        return report;
    }
    
    private String adjustments(final List<Adjustment> adjustments) {
        return adjustments.stream()
                          .map(Adjustment::toString)
                          .collect(Collectors.joining(", "));
    }
}
