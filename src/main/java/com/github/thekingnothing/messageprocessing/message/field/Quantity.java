package com.github.thekingnothing.messageprocessing.message.field;


import com.github.thekingnothing.messageprocessing.message.DecimalField;

import java.math.BigDecimal;

public class Quantity extends DecimalField{
    public static final int FIELD = 53;
    
    public static Quantity create(final BigDecimal quantity) {
        return new Quantity(quantity);
    }
    
    private Quantity(final BigDecimal data) {
        super(FIELD, data);
    }
}
