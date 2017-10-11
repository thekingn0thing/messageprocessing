package com.github.thekingnothing.messageprocessing.message;

public class Field<T> {
    
    private final int tag;
    private final T data;
    
    public Field(final int tag, final T data) {
        this.tag = tag;
        this.data = data;
    }
    
    public T getData() {
        return data;
    }
    
    public int getTag() {
        return tag;
    }
}
