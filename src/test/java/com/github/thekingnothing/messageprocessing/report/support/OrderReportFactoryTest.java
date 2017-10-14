package com.github.thekingnothing.messageprocessing.report.support;

import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.report.Report;
import com.github.thekingnothing.messageprocessing.report.support.OrderReportFactory;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderReportFactoryTest {
    
    private OrderReportFactory objectUnderTest;
    
    @Before
    public void setUp() throws Exception {
        objectUnderTest = new OrderReportFactory();
    }
    
    @Test
    public void should_create_report_with_detailing_the_number_sales_total_value_for_each_symbol() {
        final Storage storage = new DefaultStorage();
        
        storage.storeTotal("AAA", new Total(BigDecimal.ONE, BigDecimal.ONE));
        storage.storeTotal("BBB", new Total(BigDecimal.TEN, BigDecimal.TEN));
    
        final Report report = objectUnderTest.createReport(storage);
        
        assertThat(report)
            .as("Report is created")
            .isNotNull();
            
        assertThat(report.lines())
            .as("Report has detailing of the number of sales and total value.")
            .contains(
                "AAA: Quantity 1. Price 1",
                "BBB: Quantity 10. Price 10"
            );
    }
}
