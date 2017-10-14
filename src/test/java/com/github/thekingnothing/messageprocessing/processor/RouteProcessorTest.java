package com.github.thekingnothing.messageprocessing.processor;


import com.github.thekingnothing.messageprocessing.Processor;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import com.github.thekingnothing.messageprocessing.mock.MockProcessor;
import com.github.thekingnothing.messageprocessing.test.WithMessage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RouteProcessorTest implements WithMessage {
    
    private MockProcessor processor;
    private RouteProcessor objectUnderTest;
    
    @Before
    public void setUp() throws Exception {
        processor = new MockProcessor();
    
        final HashMap<MessageType, Processor> mapping = new HashMap<>();
        mapping.put(MessageType.SINGLE_ORDER, processor);
    
        objectUnderTest = new RouteProcessor(mapping);
    }
    
    @Test
    public void should_route_message_to_specified_processor() {
        final Message message = createSingleOrderMessage("AAA", BigDecimal.TEN);
        
        objectUnderTest.process(message);
        
        processor.verifyMessageProcessed(message);
    }
    
    @Test
    public void should_throw_exception_for_unknown_message_type() {
        final Message message = createQuantityOrderMessage("AAA", BigDecimal.TEN, BigDecimal.TEN);
    
        assertThatThrownBy(() ->  objectUnderTest.process(message))
            .as("Exception is thrown for unknown message type")
            .isInstanceOf(IllegalArgumentException.class);
    }
    
}