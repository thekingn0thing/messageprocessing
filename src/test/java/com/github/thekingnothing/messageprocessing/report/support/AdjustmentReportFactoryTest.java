package com.github.thekingnothing.messageprocessing.report.support;

import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.AdjustmentType;
import com.github.thekingnothing.messageprocessing.report.Report;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AdjustmentReportFactoryTest {
    
    private AdjustmentReportFactory objectUnderTest;
    
    @Before
    public void setUp() throws Exception {
        objectUnderTest = new AdjustmentReportFactory();
    }
    
    @Test
    public void should_create_report_of_adjustments_that_have_been_made_to_each_symbol() {
        final Storage storage = new DefaultStorage();

        storage.storeAdjustment(new Adjustment("AAA", AdjustmentType.ADD, BigDecimal.ONE));
        storage.storeAdjustment(new Adjustment("AAA", AdjustmentType.ADD, BigDecimal.TEN));
        storage.storeAdjustment(new Adjustment("BBB", AdjustmentType.SUB, BigDecimal.TEN));
    
        final Report report = objectUnderTest.createReport(storage);
        
        assertThat(report)
            .as("Report is created.")
            .isNotNull();
        
        assertThat(report.lines())
            .as("Report has detailing of adjustments that have been made to each_symbol")
            .contains(
                "Adjustments for AAA: +1, +10",
                "Adjustments for BBB: -10"
            );
    }
}
