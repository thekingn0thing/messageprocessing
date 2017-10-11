package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;

public class Total {
    private final BigDecimal quantity;
    private final BigDecimal totalPrice;
    
    public Total() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public Total(final BigDecimal quantity, final BigDecimal totalPrice) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public Total add(final BigDecimal quantity, final BigDecimal price) {
        final BigDecimal q = this.quantity.add(quantity);
        final BigDecimal tp = totalPrice.add(quantity.multiply(price));
        return new Total(q, tp);
    }
}
