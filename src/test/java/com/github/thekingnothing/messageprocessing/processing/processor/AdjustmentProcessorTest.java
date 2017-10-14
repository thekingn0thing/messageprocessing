package com.github.thekingnothing.messageprocessing.processing.processor;


import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.model.AdjustmentType;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.SymbolAdjustment;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import com.github.thekingnothing.messageprocessing.test.WithMessage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class AdjustmentProcessorTest implements WithMessage {
    
    private AdjustmentProcessor objectUnderTest;
    private String symbol;
    private Storage storage;
    
    @Before
    public void setUp() throws Exception {
        symbol = "ZXZ";
        
        storage = new DefaultStorage();
        objectUnderTest = new AdjustmentProcessor(storage);
    }
    
    
    @Test
    public void should_process_and_store_adjustment_message() {
        final int adjustmentType = 1;
        final BigDecimal price = new BigDecimal("17.1");
        final Message message = createAdjustmentMessage(symbol, adjustmentType, price);
        
        objectUnderTest.process(message);
    
        assertThat(storage.allAdjustment())
            .as("Message Type 3 is recorded.")
            .flatExtracting(SymbolAdjustment::getAdjustments)
            .containsExactly(new Adjustment(symbol, AdjustmentType.ADD, price));
    }
    
    @Test
    public void should_process_add_adjustment_message_in_add_price_correction_to_each_order_for_given_symbol() {
        final String otherSymbol = "OTHER";
        
        storage.storeOrder(new Order(symbol, BigDecimal.ONE, BigDecimal.TEN));
        storage.storeOrder(new Order(symbol, new BigDecimal("20"), new BigDecimal("10.5")));
        storage.storeOrder(new Order(otherSymbol, BigDecimal.ONE, new BigDecimal("1.5")));
    
        final int adjustmentType = 1;
        final BigDecimal price = new BigDecimal("1.1");
        final Message message = createAdjustmentMessage(symbol, adjustmentType, price);
    
        objectUnderTest.process(message);
        
        assertThat(storage.allOrders())
            .as("Adjustment is applied to all order with given symbol")
            .extracting(Order::getSymbol, Order::getQuantity, Order::getPrice)
            .containsExactly(
                tuple(otherSymbol, BigDecimal.ONE, new BigDecimal("1.5")),
                tuple(symbol, BigDecimal.ONE, new BigDecimal("11.1")),
                tuple(symbol, new BigDecimal("20"), new BigDecimal("11.6"))
            );
    }
    
    
    @Test
    public void should_process_add_adjustment_message_and_update_total() {
    
        final String otherSymbol = "OTHER";
    
        storage.storeOrder(new Order(symbol, BigDecimal.ONE, BigDecimal.TEN));
        storage.storeOrder(new Order(symbol, new BigDecimal("20"), new BigDecimal("10.5")));
        storage.storeOrder(new Order(otherSymbol, BigDecimal.ONE, new BigDecimal("1.5")));
    
        storage.storeTotal(symbol, new Total(new BigDecimal("21"), new BigDecimal("220")));
        
        final int adjustmentType = 1;
        final BigDecimal price = new BigDecimal("1.1");
        final Message message = createAdjustmentMessage(symbol, adjustmentType, price);
    
        objectUnderTest.process(message);
    
        assertThat(storage.getTotal(symbol))
            .as("Adjustment is applied to total.")
            .extracting(Total::getQuantity, Total::getTotalValue)
            .containsExactly(new BigDecimal("21"), new BigDecimal("243.1"));
    }
}