package com.github.thekingnothing.messageprocessing.message;


public class IntField extends Field<Integer> {
    public static final IntField NULL = new IntField(-1, null);
    
    public IntField(final int tag, final Integer data) {
        super(tag, data);
    }
}
