package com.github.thekingnothing.messageprocessing.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
    private final String symbol;
    private final BigDecimal quantity;
    private final BigDecimal price;
    
    public Order(final String symbol, final BigDecimal quantity, final BigDecimal price) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Order)) { return false; }
        final Order order = (Order) o;
        return Objects.equals(getSymbol(), order.getSymbol()) &&
                   Objects.equals(getQuantity(), order.getQuantity()) &&
                   Objects.equals(getPrice(), order.getPrice());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getQuantity(), getPrice());
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
