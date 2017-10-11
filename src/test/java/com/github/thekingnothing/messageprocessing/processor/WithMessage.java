package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import com.github.thekingnothing.messageprocessing.message.field.AdjustmentOperationType;
import com.github.thekingnothing.messageprocessing.message.field.Price;
import com.github.thekingnothing.messageprocessing.message.field.Quantity;
import com.github.thekingnothing.messageprocessing.message.field.Symbol;

import java.math.BigDecimal;

public interface WithMessage {
    
    default Message createQuantityOrderMessage(final String symbol, final BigDecimal quantity, final BigDecimal price) {
        final Message message = new Message(MessageType.QUANTITY_ORDER);
        message.set(Symbol.create(symbol));
        message.set(Quantity.create(quantity));
        message.set(Price.create(price));
        return message;
    }
    
    default Message createAdjustmentMessage(final String symbol, final int adjustmentType, final BigDecimal price) {
        final Message message = new Message(MessageType.ADJUSTMENT);
        message.set(Symbol.create(symbol));
        message.set(AdjustmentOperationType.create(adjustmentType));
        message.set(Price.create(price));
        return message;
    }
    
    default Message createSingleOrderMessage(final String symbol, final BigDecimal price) {
        final Message message = new Message(MessageType.SINGLE_ORDER);
        message.set(Symbol.create(symbol));
        message.set(Price.create(price));
        return message;
    }
    
    
}
