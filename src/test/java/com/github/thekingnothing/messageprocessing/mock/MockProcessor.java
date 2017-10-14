package com.github.thekingnothing.messageprocessing.mock;

import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.Processor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MockProcessor implements Processor {
    
    private final List<Message> messages;
    
    public MockProcessor() {messages = new ArrayList<>();}
    
    @Override
    public void process(final Message message) {
        messages.add(message);
    }
    
    public void verifyMessageProcessed(final Message message) {
        assertThat(messages)
            .as("Verify message `%s` has been processed.", message)
            .containsExactly(message);
    }
}
