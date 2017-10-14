package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;

public enum AdjustmentType {
    ADD(1) {
        @Override
        public BigDecimal apply(final BigDecimal oldPrice, final BigDecimal adjustmentPrice) {
            return oldPrice.add(adjustmentPrice);
        }
    
        @Override
        public BigDecimal apply(final Total total, final BigDecimal adjustmentPrice) {
            return total.getTotalValue().add(total.getQuantity().multiply(adjustmentPrice));
        }
    
        @Override
        public String toString() {
            return "+";
        }
    },
    
    SUB(2) {
        @Override
        public BigDecimal apply(final BigDecimal oldPrice, final BigDecimal adjustmentPrice) {
            return oldPrice.subtract(adjustmentPrice);
        }
    
        @Override
        public BigDecimal apply(final Total total, final BigDecimal newPrice) {
            return total.getTotalValue().subtract(total.getQuantity().multiply(newPrice));
        }
    
        @Override
        public String toString() {
            return "-";
        }
    },
    
    MULT(3) {
        @Override
        public BigDecimal apply(final BigDecimal oldPrice, final BigDecimal newPrice) {
            return oldPrice.multiply(newPrice);
        }
    
        @Override
        public BigDecimal apply(final Total total, final BigDecimal newPrice) {
            return total.getTotalValue().multiply(newPrice);
        }
    
        @Override
        public String toString() {
            return "*";
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
    
    public Integer getOperationType() {
        return operationType;
    }
    
    public abstract BigDecimal apply(BigDecimal oldPrice, BigDecimal newPrice);
    public abstract BigDecimal apply(Total total, BigDecimal newPrice);
}
