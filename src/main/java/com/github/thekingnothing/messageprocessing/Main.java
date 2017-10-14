package com.github.thekingnothing.messageprocessing;


import com.github.thekingnothing.messageprocessing.exception.RejectionException;
import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.processing.processor.MainProcessor;

import java.io.BufferedReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    
    private static final Logger LOGGER = LogFactory.getLogger();
    
    public static void main(String[] args) throws Exception {
        
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        
        final Configuration configuration = new Configuration();
        final MainProcessor mainProcessor = configuration.mainProcessor();
        
        final URL resource = contextClassLoader.getResource("message.fix");
        final Path path = Paths.get(resource.toURI());
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines()
                  .forEach(
                      message -> {
                          try {
                              mainProcessor.process(Message.fromString(message));
                          } catch (RejectionException e) {
                              LOGGER.info("Message `%s` has not been processed. Reason: %s", message, e.getMessage());
                          } catch (Exception e) {
                              LOGGER.error(e.getMessage(), e);
                          }
                      }
                  );
        }
    }
    
}
