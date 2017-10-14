package com.github.thekingnothing.messageprocessing.message.field;


import com.github.thekingnothing.messageprocessing.message.IntField;

public class AdjustmentOperationType extends IntField {
    public static final int FIELD = 334;
    
    public static AdjustmentOperationType create(final int adjustmentType) {
        return new AdjustmentOperationType(adjustmentType);
    }
    
    public AdjustmentOperationType(final Integer adjustmentType) {
        super(FIELD, adjustmentType);
    }
}
