package com.github.thekingnothing.messageprocessing.message;

import java.math.BigDecimal;

public class DecimalField extends Field<BigDecimal> {
    public static DecimalField NULL = new DecimalField(-1, null);
    
    public DecimalField(final int tag, final BigDecimal data) {super(tag, data);}
}
