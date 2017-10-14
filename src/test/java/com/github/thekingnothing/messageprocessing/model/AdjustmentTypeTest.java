package com.github.thekingnothing.messageprocessing.model;


import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AdjustmentTypeTest {
    
    @Test
    public void should_add_adjustment_price_to_order_and_return_new_price() {
        final BigDecimal oldPrice = BigDecimal.TEN;
        final BigDecimal newPrice = BigDecimal.ONE;
        
        assertThat(AdjustmentType.ADD.apply(oldPrice, newPrice))
            .as("New price calculated")
            .isEqualTo(new BigDecimal("11"));
    }
    
    @Test
    public void should_subtract_adjustment_price_to_order_and_return_new_price() {
        final BigDecimal oldPrice = BigDecimal.TEN;
        final BigDecimal newPrice = BigDecimal.ONE;
        
        assertThat(AdjustmentType.SUB.apply(oldPrice, newPrice))
            .as("New price calculated")
            .isEqualTo(new BigDecimal("9"));
    }
    
    @Test
    public void should_multiply_adjustment_price_to_order_and_return_new_price() {
        final BigDecimal oldPrice = BigDecimal.TEN;
        final BigDecimal newPrice = BigDecimal.TEN;
        
        assertThat(AdjustmentType.MULT.apply(oldPrice, newPrice))
            .as("New price calculated")
            .isEqualTo(new BigDecimal("100"));
    }
    
    @Test
    public void should_calculate_new_total_base_on_previous_total_and_operation_type() {
    
        Total total = new Total(new BigDecimal("22"), new BigDecimal("700"));
    
        assertThat(AdjustmentType.ADD.apply(total, new BigDecimal("3")))
            .as("New total calculated")
            .isEqualTo(new BigDecimal("766"));
    
        assertThat(AdjustmentType.SUB.apply(total, new BigDecimal("3")))
            .as("New total calculated")
            .isEqualTo(new BigDecimal("634"));
        
        assertThat(AdjustmentType.MULT.apply(total, new BigDecimal("3")))
            .as("New total calculated")
            .isEqualTo(new BigDecimal("2100"));
    }
    
}