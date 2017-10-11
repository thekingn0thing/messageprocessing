package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import com.github.thekingnothing.messageprocessing.message.field.Price;
import com.github.thekingnothing.messageprocessing.message.field.Symbol;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleOrderProcessorTest implements WithMessage{
    
    private SingleOrderProcessor objectUnderTest;
    private String symbol;
    private Storage storage;
    
    @Before
    public void setUp() throws Exception {
        symbol = "ZXZ";
        
        storage = new DefaultStorage();
        objectUnderTest = new SingleOrderProcessor(storage);
    }
    
    @Test
    public void should_process_single_order_and_add_order_to_storage() {
        final BigDecimal price = new BigDecimal("14.1");
        final Message message = createSingleOrderMessage(symbol, price);
        
        objectUnderTest.process(message);
        
        assertThat(storage.allOrders())
            .as("Message Type 1 is recorded.")
            .containsExactly(new Order(symbol, BigDecimal.ONE, price));
    }
    
    @Test
    public void should_update_total_for_symbol_and_addquantity_and_price() {
        final BigDecimal price = new BigDecimal("14.1");
        final Message message = createSingleOrderMessage(symbol, price);
    
        storage.storeTotal(symbol, new Total(BigDecimal.TEN, BigDecimal.TEN));
        
        objectUnderTest.process(message);
    
        assertThat(storage.getTotal(symbol))
            .as("Total for symbol is updated.")
            .extracting(Total::getQuantity, Total::getTotalPrice)
            .containsExactly(new BigDecimal("11"), new BigDecimal("24.1"));
    }
}