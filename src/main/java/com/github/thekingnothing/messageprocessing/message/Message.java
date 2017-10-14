package com.github.thekingnothing.messageprocessing.message;

import com.github.thekingnothing.messageprocessing.message.dictionary.MessageDictionary;
import com.github.thekingnothing.messageprocessing.message.field.MessageTypeField;
import com.github.thekingnothing.messageprocessing.message.parser.MessageParser;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Message {
    public static final char FIELD_SEPARATOR = '\001';
    
    private static final MessageDictionary messageDictionary = MessageDictionary.create();
    
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
    
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        appendField(sb, MessageTypeField.create(messageType));
        
        values.values().forEach(field -> appendField(sb, field));
        
        return sb.toString();
    }
    
    private static void appendField(StringBuilder buffer, Field<?> field) {
        if (field != null) {
            field.toString(buffer);
            buffer.append(FIELD_SEPARATOR);
        }
    }
    
    public static Message fromString(final String message) throws Exception{
        return new MessageParser(messageDictionary).parse(message);
    }
}
