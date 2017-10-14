package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.Processor;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.storage.Storage;

public abstract class OrderProcessor implements Processor {
    protected final Storage storage;
    
    OrderProcessor(final Storage storage) {this.storage = storage;}
    
    protected void processOrder(final Order order) {
        storage.storeOrder(order);
        
        final Total current = storage.getTotal(order.getSymbol());
        storage.storeTotal(order.getSymbol(), current.add(order.getQuantity(), order.getPrice()));
    }
}
