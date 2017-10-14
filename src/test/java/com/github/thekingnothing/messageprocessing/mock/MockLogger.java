package com.github.thekingnothing.messageprocessing.mock;

import com.github.thekingnothing.messageprocessing.log.Logger;
import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MockLogger implements Logger {
    
    private final List<String> messages;
    
    public MockLogger() {messages = new ArrayList<>();}
    
    @Override
    public void info(final String message) {
        messages.add(message);
    }
    
    @Override
    public void info(final String message, final Object... args) {
        info(String.format(message, args));
    }
    
    public void assertLogContains(final String message) {
        final Condition<List<?>> logContainsRecord = new Condition<List<?>>() {
            
            @Override
            public Description description() {
                return new TextDescription("Except message contains %s.", message);
            }
            
            @Override
            public boolean matches(final List<?> values) {
                for (Object value : values) {
                    if (((String) value).contains(message)) {
                        return true;
                    }
                }
                return false;
            }
        };
        
        assertThat(messages)
            .as("Log contains message.")
            .withFailMessage(messages + " does not contain " + message)
            .is(logContainsRecord);
    }
}
