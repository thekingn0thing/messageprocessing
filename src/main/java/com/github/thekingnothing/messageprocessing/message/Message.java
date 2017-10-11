package com.github.thekingnothing.messageprocessing.message;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Message {
    private final MessageType messageType;
    private final NavigableMap<Integer, Field<?>> values;
    
    public Message(final MessageType messageType) {
        this.messageType = messageType;
        values = new TreeMap<>();
    }
    
    public MessageType getMessageType() {
        return messageType;
    }
    
    public <T> void set(final Field<T> field) {
        values.put(field.getTag(), field);
    }
    
    public String getString(final int field) {
        return (String) values.getOrDefault(field, StringField.NULL).getData();
    }
    
    public BigDecimal getDecimal(final int field) {
        return (BigDecimal) values.getOrDefault(field, DecimalField.NULL).getData();
    }
    
    public Integer getInteger(final int field) {
        return (Integer) values.getOrDefault(field, IntField.NULL).getData();
    }
}
