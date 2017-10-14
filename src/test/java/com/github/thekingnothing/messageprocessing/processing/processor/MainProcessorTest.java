package com.github.thekingnothing.messageprocessing.processing.processor;

import com.github.thekingnothing.messageprocessing.processing.PostProcessor;
import com.github.thekingnothing.messageprocessing.exception.RejectionException;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.mock.MockLogger;
import com.github.thekingnothing.messageprocessing.mock.MockProcessor;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import com.github.thekingnothing.messageprocessing.test.WithMessage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MainProcessorTest implements WithMessage {
    
    private MockLogger logger;
    
    @Before
    public void setUp() throws Exception {
        logger = new MockLogger();
        LogFactory.factory = () -> logger;
    }
    
    @Test
    public void should_call_post_processors_in_add_order() {
        final AtomicInteger counter = new AtomicInteger();
        final MainProcessor objectUnderTest = new MainProcessor(new DefaultStorage(), new MockProcessor());
        final MockPostProcessor secondPostProcessor = new MockPostProcessor(counter);
    
        objectUnderTest.addPostProcessor(new MockPostProcessor(counter));
        objectUnderTest.addPostProcessor(secondPostProcessor);
        
        objectUnderTest.process(createSingleOrderMessage("IBM", BigDecimal.TEN));
        
        secondPostProcessor.assertThatPostProcessorCalledSecond();
    }
    
    @Test
    public void should_delegate_message_to_delegator() {
        final MockProcessor delegator = new MockProcessor();
        final MainProcessor objectUnderTest = new MainProcessor(new DefaultStorage(), delegator);
        
        final Message message = createAnyOrder();
        
        objectUnderTest.process(message);
        
        delegator.verifyMessageProcessed(message);
    }
    
    @Test
    public void should_stop_processing_after_n_and_log_message() {
        final int messageLimit = 3;
        
        final MainProcessor objectUnderTest = new MainProcessor(new DefaultStorage(), new MockProcessor(), messageLimit);
    
        for (int i = 0; i < messageLimit; i++) {
            objectUnderTest.process(createAnyOrder());
        }
        
        logger.assertLogContains("Message processing is pausing.");
    }
    
    @Test
    public void should_throw_exception_when_message_processing_is_paused() {
    
        final int messageLimit = 3;
    
        final MainProcessor objectUnderTest = new MainProcessor(new DefaultStorage(), new MockProcessor(), messageLimit);
    
        for (int i = 0; i < messageLimit; i++) {
            objectUnderTest.process(createAnyOrder());
        }
        
        assertThatThrownBy(() -> objectUnderTest.process(createAnyOrder()))
            .as("Message is not accepted during pausing")
            .isExactlyInstanceOf(RejectionException.class)
            .hasMessageContaining("Message processing is paused.");
    }
    
    private Message createAnyOrder() {
        return createSingleOrderMessage("AAA", BigDecimal.TEN);
    }
    
    private static class MockPostProcessor implements PostProcessor {
        private final AtomicInteger counter;
        private int processTime;
    
        private MockPostProcessor(final AtomicInteger counter) {
            this.counter = counter;
        }
    
        @Override
        public void afterMessage(final Storage storage) {
            processTime = counter.getAndIncrement();
        }
    
        private void assertThatPostProcessorCalledSecond() {
            assertThat(processTime)
                .as("The processor called in order.")
                .isEqualTo(1);
        }
    }
}
