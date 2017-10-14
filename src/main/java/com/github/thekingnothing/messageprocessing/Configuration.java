package com.github.thekingnothing.messageprocessing;

import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.processing.ProcessorConfiguration;
import com.github.thekingnothing.messageprocessing.processing.processor.MainProcessor;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;

public class Configuration {
    
    private final ProcessorConfiguration processorConfiguration;
    
    public Configuration() {
        processorConfiguration = new ProcessorConfiguration(10, 50, 50);
    }
    
    public Storage storage() {
        return new DefaultStorage();
    }
    
    public MainProcessor mainProcessor() {
        final Storage storage = storage();
        return processorConfiguration.mainProcessor(storage, processorConfiguration.routeProcessor(storage), LogFactory.getLogger());
    }
    
}
