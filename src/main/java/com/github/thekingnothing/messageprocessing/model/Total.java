package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;

public class Total {
    private final BigDecimal quantity;
    private final BigDecimal totalValue;
    
    public Total() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public Total(final BigDecimal quantity, final BigDecimal totalValue) {
        this.quantity = quantity;
        this.totalValue = totalValue;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public BigDecimal getTotalValue() {
        return totalValue;
    }
    
    public Total add(final BigDecimal quantity, final BigDecimal price) {
        final BigDecimal q = this.quantity.add(quantity);
        final BigDecimal tp = totalValue.add(quantity.multiply(price));
        return new Total(q, tp);
    }
}
