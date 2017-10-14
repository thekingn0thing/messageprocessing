package com.github.thekingnothing.messageprocessing.message.parser;

import com.github.thekingnothing.messageprocessing.message.Field;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import com.github.thekingnothing.messageprocessing.message.dictionary.MessageDictionary;

public class MessageParser {
    private final MessageDictionary messageDictionary;
    
    public MessageParser(final MessageDictionary messageDictionary) {
        this.messageDictionary = messageDictionary;
    }
    
    public Message parse(final String message) throws Exception {
        final String[] pairs = message.split(String.valueOf(Message.FIELD_SEPARATOR));
        
        Field<?>[] fields = new Field[pairs.length];
        
        for (int i = 0; i < pairs.length; i++) {
            final String[] fieldValue = pairs[i].split("=");
            fields[i] = messageDictionary.parseField(Integer.parseInt(fieldValue[0]), fieldValue[1]);
        }
        
        final Message msg = new Message(MessageType.find((Integer)fields[0].getData()));
        
        for (int i = 1; i < fields.length; i++) {
            msg.set(fields[i]);
        }
        
        return msg;
    }
}
