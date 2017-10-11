package com.github.thekingnothing.messageprocessing.model;

import java.util.ArrayList;
import java.util.List;

public final class SymbolAdjustment {
    
    private final String symbol;
    private final List<Adjustment> adjustments;
    
    public SymbolAdjustment(final String symbol, final List<Adjustment> adjustments) {
        this.symbol = symbol;
        this.adjustments = adjustments;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public List<Adjustment> getAdjustments() {
        return new ArrayList<>(adjustments);
    }
}
