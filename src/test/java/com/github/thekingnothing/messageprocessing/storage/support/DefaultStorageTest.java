package com.github.thekingnothing.messageprocessing.storage.support;

import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.AdjustmentType;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.SymbolAdjustment;
import com.github.thekingnothing.messageprocessing.model.Total;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultStorageTest {
    
    private DefaultStorage objectUnderTest;
    
    @Before
    public void setUp() throws Exception {
        objectUnderTest = new DefaultStorage();
    }
    
    @Test
    public void should_store_and_return_orders() {
        final Order order = new Order("DDD", BigDecimal.ONE, BigDecimal.TEN);
        objectUnderTest.storeOrder(order);
        
        assertThat(objectUnderTest.allOrders())
            .as("All sales must be recorded.")
            .containsExactly(order);
    }
    
    @Test
    public void should_store_and_return_orders_for_symbol() {
        final String symbol = "DDD";
        final Order order = new Order(symbol, BigDecimal.ONE, BigDecimal.TEN);
        
        objectUnderTest.storeOrder(order);
        objectUnderTest.storeOrder(new Order("DDD2", BigDecimal.ONE, BigDecimal.TEN));
        
        assertThat(objectUnderTest.ordersForSymbol(symbol))
            .as("Orders are recorded by symbol")
            .containsExactly(order);
    }
    
    
    @Test
    public void should_remove_all_order_for_symbol() {
        final String symbol = "DDD";
        final Order order = new Order(symbol, BigDecimal.ONE, BigDecimal.TEN);
        final Order secondOrder = new Order("DDD2", BigDecimal.ONE, BigDecimal.TEN);
    
        objectUnderTest.storeOrder(order);
        objectUnderTest.storeOrder(order);
        objectUnderTest.storeOrder(secondOrder);
        
        objectUnderTest.truncateOrders(symbol);
        
        assertThat(objectUnderTest.allOrders())
            .as("Orders are removed for symbol")
            .containsExactly(secondOrder);
    }
    
    
    @Test
    public void should_store_and_return_adjustment_per_symbol() {
        final Adjustment adjustmentDDD1 = new Adjustment("DDD", AdjustmentType.ADD, BigDecimal.TEN);
        final Adjustment adjustmentDDD2 = new Adjustment("DDD", AdjustmentType.ADD, BigDecimal.TEN);
        final Adjustment adjustmentIBM = new Adjustment("IBM", AdjustmentType.ADD, BigDecimal.TEN);
        
        objectUnderTest.storeAdjustment(adjustmentDDD1);
        objectUnderTest.storeAdjustment(adjustmentIBM);
        objectUnderTest.storeAdjustment(adjustmentDDD2);
        
        assertThat(objectUnderTest.allAdjustment())
            .as("All adjustments have been store for symbol.")
            .flatExtracting(SymbolAdjustment::getAdjustments)
            .containsExactlyInAnyOrder(adjustmentDDD1, adjustmentDDD2, adjustmentIBM);
    }
    
    @Test
    public void should_store_and_return_total_per_symbol() {
    
        final Total total = new Total();
        final String symbol = "symbol";
        objectUnderTest.storeTotal(symbol, total);
        
        assertThat(objectUnderTest.getTotal(symbol))
            .as("Total is stored and returned for symbol")
            .isSameAs(total);
    }
    
}