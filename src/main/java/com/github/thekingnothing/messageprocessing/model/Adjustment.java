package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Adjustment {
    private final String symbol;
    private final AdjustmentType adjustmentType;
    private final BigDecimal price;
    
    public Adjustment(final String symbol, final AdjustmentType adjustmentType, final BigDecimal price) {
        this.symbol = symbol;
        this.adjustmentType = adjustmentType;
        this.price = price;
    }
    
    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Adjustment)) { return false; }
        final Adjustment that = (Adjustment) o;
        return Objects.equals(getSymbol(), that.getSymbol()) &&
                   getAdjustmentType() == that.getAdjustmentType() &&
                   Objects.equals(getPrice(), that.getPrice());
    }
    
    public Order apply(final Order order) {
        return new Order(order.getSymbol(), order.getQuantity(), adjustmentType.apply(order.getPrice(), price));
    }
    
    public Total apply(final Total total) {
        return new Total(total.getQuantity(), adjustmentType.apply(total, price));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getAdjustmentType(), getPrice());
    }
    
    @Override
    public String toString() {
        return getAdjustmentType() + getPrice().toString();
    }
}
