package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;

public enum AdjustmentType {
    ADD(1) {
        @Override
        public BigDecimal apply(final BigDecimal oldPrice, final BigDecimal newPrice) {
            return oldPrice.add(newPrice);
        }
    
        @Override
        public BigDecimal apply(final Total total, final BigDecimal newPrice) {
            return total.getTotalPrice().add(total.getQuantity().multiply(newPrice));
        }
    },
    
    SUB(2) {
        @Override
        public BigDecimal apply(final BigDecimal oldPrice, final BigDecimal newPrice) {
            return oldPrice.subtract(newPrice);
        }
    
        @Override
        public BigDecimal apply(final Total total, final BigDecimal newPrice) {
            return total.getTotalPrice().add(total.getQuantity().multiply(newPrice));
        }
    };
    
    public static AdjustmentType find(final Integer type) {
        for (AdjustmentType adjustmentType : AdjustmentType.values()) {
            if (adjustmentType.operationType.equals(type)) {
                return adjustmentType;
            }
        }
        throw new IllegalArgumentException("Unknown operation type " + type);
    }
    
    private final Integer operationType;
    
    AdjustmentType(final Integer operationType) {
        this.operationType = operationType;
    }
    
    public abstract BigDecimal apply(BigDecimal oldPrice, BigDecimal newPrice);
    public abstract BigDecimal apply(Total total, BigDecimal newPrice);
}
