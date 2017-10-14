package com.github.thekingnothing.messageprocessing.processor;

import com.github.thekingnothing.messageprocessing.Processor;
import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;

import java.util.Map;

public class RouteProcessor implements Processor {
    
    private static final Logger LOGGER = LogFactory.getLogger();
    
    private final Map<MessageType, Processor> processors;
    
    RouteProcessor(final Map<MessageType, Processor> processors) {
        this.processors = processors;
    }
    
    @Override
    public void process(final Message message) {
        LOGGER.info("Receive message %s", message);
        
        final Processor processor = processors.get(message.getMessageType());
        if (processor == null){
            throw new IllegalArgumentException("Unknown message type " + message.getMessageType());
        }
        processor.process(message);
    }
}
