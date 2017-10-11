package com.github.thekingnothing.messageprocessing.processor;


import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RouteProcessorTest implements WithMessage{
    
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
    
    private static class MockProcessor implements Processor {
    
        private final List<Message> messages;
    
        private MockProcessor() {messages = new ArrayList<>();}
    
        @Override
        public void process(final Message message) {
            messages.add(message);
        }
        
        private void verifyMessageProcessed(final Message message){
            assertThat(messages)
                .as("Verify message `%s` has been processed.", message)
                .containsExactly(message);
        }
    }
    
}