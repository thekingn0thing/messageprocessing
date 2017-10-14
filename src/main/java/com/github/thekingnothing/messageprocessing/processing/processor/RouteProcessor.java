package com.github.thekingnothing.messageprocessing.processing.processor;

import com.github.thekingnothing.messageprocessing.processing.Processor;
import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;

import java.util.Map;

public class RouteProcessor implements Processor {
    private final Map<MessageType, Processor> processors;
    
    public RouteProcessor(final Map<MessageType, Processor> processors) {
        this.processors = processors;
    }
    
    @Override
    public void process(final Message message) {
        final Processor processor = processors.get(message.getMessageType());
        if (processor == null){
            throw new IllegalArgumentException("Unknown message type " + message.getMessageType());
        }
        processor.process(message);
    }
}
