package com.github.thekingnothing.messageprocessing.message.field;


import com.github.thekingnothing.messageprocessing.message.IntField;

public class AdjustmentOperationType extends IntField {
    public static final int FIELD = 334;
    
    public static AdjustmentOperationType create(final int adjustmentType) {
        return new AdjustmentOperationType(adjustmentType);
    }
    
    private AdjustmentOperationType(final int adjustmentType) {
        super(FIELD, adjustmentType);
    }
}
