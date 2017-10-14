package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.model.AdjustmentType;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.model.Total;
import com.github.thekingnothing.messageprocessing.Processor;
import com.github.thekingnothing.messageprocessing.model.Adjustment;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.field.AdjustmentOperationType;
import com.github.thekingnothing.messageprocessing.message.field.Price;
import com.github.thekingnothing.messageprocessing.message.field.Symbol;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.math.BigDecimal;
import java.util.List;


public class AdjustmentProcessor implements Processor {
    
    private final Storage storage;
    
    public AdjustmentProcessor(final Storage storage) {
        this.storage = storage;
    }
    
    @Override
    public void process(final Message message) {
    
        final Adjustment adjustment = getAdjustment(message);
    
        final String symbol = adjustment.getSymbol();
        
        updateOrders(adjustment, symbol);
    
        final Total total = storage.getTotal(symbol);
        storage.storeTotal(symbol, adjustment.apply(total));
    
    }
    
    private void updateOrders(final Adjustment adjustment, final String symbol) {
        final List<Order> orders = storage.ordersForSymbol(symbol);
        
        storage.truncateOrders(symbol);
        
        for (Order order : orders) {
            storage.storeOrder(adjustment.apply(order));
        }
    }
    
    private Adjustment getAdjustment(final Message message) {
        final String symbol = message.getString(Symbol.FIELD);
        final AdjustmentType adjustmentType = AdjustmentType.find(message.getInteger(AdjustmentOperationType.FIELD));
        final BigDecimal price = message.getDecimal(Price.FIELD);
    
        final Adjustment adjustment = new Adjustment(symbol, adjustmentType, price);
        storage.storeAdjustment(adjustment);
        return adjustment;
    }
}
