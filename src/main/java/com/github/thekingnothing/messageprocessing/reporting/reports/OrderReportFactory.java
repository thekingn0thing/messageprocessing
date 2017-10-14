package com.github.thekingnothing.messageprocessing.reporting.reports;

import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.reporting.Report;
import com.github.thekingnothing.messageprocessing.reporting.ReportFactory;
import com.github.thekingnothing.messageprocessing.reporting.support.StringLineReport;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Map.Entry;

public class OrderReportFactory implements ReportFactory {
    @Override
    public Report createReport(final Storage storage) {
        final StringLineReport report = new StringLineReport();
    
        report.append("___________________________________");
        report.append("| Symbol | Quantity | Total Value |");
        for (Entry<String, Total> entry : storage.allTotals().entrySet()) {
            final Total total = entry.getValue();
            final String symbol = StringUtils.central(entry.getKey(), 8);
            final String quantity = StringUtils.central(total.getQuantity().toString(), 10);
            final String totalValue = StringUtils.central(total.getTotalValue().toString(), 13);
            
            report.append("|" + symbol + "|" + quantity + "|" + totalValue + "|");
            report.append("|________|__________|_____________|");
        }
        return report;
    }
}
