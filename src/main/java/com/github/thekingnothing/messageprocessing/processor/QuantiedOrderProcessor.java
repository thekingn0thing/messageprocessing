package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.field.Price;
import com.github.thekingnothing.messageprocessing.message.field.Quantity;
import com.github.thekingnothing.messageprocessing.message.field.Symbol;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.math.BigDecimal;

public class QuantiedOrderProcessor extends OrderProcessor {
    
    QuantiedOrderProcessor(final Storage storage) {
        super(storage);
    }
    
    @Override
    public void process(final Message message) {
        final String symbol = message.getString(Symbol.FIELD);
        final BigDecimal quantity = message.getDecimal(Quantity.FIELD);
        final BigDecimal price = message.getDecimal(Price.FIELD);
        
        processOrder(new Order(symbol, quantity, price));
    }
}
