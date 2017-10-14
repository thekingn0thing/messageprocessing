package com.github.thekingnothing.messageprocessing.message.field;


import com.github.thekingnothing.messageprocessing.message.IntField;
import com.github.thekingnothing.messageprocessing.message.MessageType;

public class MessageTypeField extends IntField {
    public static final int FIELD = 35;
    
    public static MessageTypeField create(final MessageType messageType) {
        return new MessageTypeField(messageType);
    }
    
    public MessageTypeField(final Integer data) {
        super(FIELD, data);
    }
    
    public MessageTypeField(MessageType messageType) {
        super(FIELD, messageType.getType());
    }
}
