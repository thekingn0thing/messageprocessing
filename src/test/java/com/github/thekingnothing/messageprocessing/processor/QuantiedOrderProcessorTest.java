package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class QuantiedOrderProcessorTest implements WithMessage {
    
    private QuantiedOrderProcessor objectUnderTest;
    private String symbol;
    private Storage storage;
    
    @Before
    public void setUp() throws Exception {
        symbol = "ZXZ";
        
        storage = new DefaultStorage();
        
        objectUnderTest = new QuantiedOrderProcessor(storage);
    }
    
    
    @Test
    public void should_process_and_store_order() {
        final BigDecimal quantity = new BigDecimal("23");
        final BigDecimal price = new BigDecimal("17.1");
        final Message message = createQuantityOrderMessage(symbol, quantity, price);
        
        objectUnderTest.process(message);
        
        assertThat(storage.allOrders())
            .as("Message Type 2 is recorded.")
            .containsExactly(new Order(symbol, quantity, price));
    }
    
    
    @Test
    public void should_update_total_for_symbol_and_addquantity_and_price() {
        final BigDecimal quantity = new BigDecimal("23");
        final BigDecimal price = new BigDecimal("14.1");
        final Message message = createQuantityOrderMessage(symbol, quantity, price);
        
        storage.storeTotal(symbol, new Total(BigDecimal.TEN, BigDecimal.TEN));
        
        objectUnderTest.process(message);
        
        assertThat(storage.getTotal(symbol))
            .as("Total for symbol is updated.")
            .extracting(Total::getQuantity, Total::getTotalPrice)
            .containsExactly(new BigDecimal("33"), new BigDecimal("334.3"));
    }
}