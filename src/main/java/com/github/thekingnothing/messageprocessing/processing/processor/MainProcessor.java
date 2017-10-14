package com.github.thekingnothing.messageprocessing.processing.processor;


import com.github.thekingnothing.messageprocessing.processing.PostProcessor;
import com.github.thekingnothing.messageprocessing.processing.Processor;
import com.github.thekingnothing.messageprocessing.exception.RejectionException;
import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class MainProcessor implements Processor {
    
    private static final int DEFAULT_MESSAGE_LIMIT = 50;
    
    private Logger logger = LogFactory.getLogger();
    
    private final Storage storage;
    private final Processor delegator;
    private final int messageLimit;
    private final List<PostProcessor> postProcessors;
    private int counter;
    
    public MainProcessor(final Storage storage, final Processor delegator) {
        this(storage, delegator, DEFAULT_MESSAGE_LIMIT);
    }
    
    public MainProcessor(final Storage storage, final Processor delegator, final int messageLimit) {
        this.storage = storage;
        this.delegator = delegator;
        this.messageLimit = messageLimit;
        postProcessors = new ArrayList<>();
    }
    
    @Override
    public void process(final Message message) {
        assertThatMessageCanBeAccepted();
    
        incrementMessageCounter();
        
        delegator.process(message);
        
        logPauseMessage();
        postProcess();
    }
    
    public void addPostProcessor(final PostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }
    
    private void incrementMessageCounter() {
        ++counter;
    }
    
    private void assertThatMessageCanBeAccepted() {
        if (isPaused()){
            throw new RejectionException("Message processing is paused.");
        }
    }
    
    private void logPauseMessage() {
        if (isPaused()){
            logger.info("Message processing is pausing.");
        }
    }
    
    private void postProcess() {
        for (PostProcessor postProcessor : postProcessors) {
            postProcessor.afterMessage(storage);
        }
    }
    
    private boolean isPaused() {
        return counter == messageLimit;
    }
}
