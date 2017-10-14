package com.github.thekingnothing.messageprocessing.message.field;

import com.github.thekingnothing.messageprocessing.message.DecimalField;

import java.math.BigDecimal;

public class Price extends DecimalField {
    public static final int FIELD = 44;
    
    public static Price create(final BigDecimal price) {
        return new Price(price);
    }
    
    public Price(final BigDecimal data) {
        super(FIELD, data);
    }
}
