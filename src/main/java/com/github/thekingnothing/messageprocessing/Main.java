package com.github.thekingnothing.messageprocessing;


import com.github.thekingnothing.messageprocessing.exception.RejectionException;
import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.log.support.LogFactory;
import com.github.thekingnothing.messageprocessing.message.Message;
import com.github.thekingnothing.messageprocessing.processing.processor.MainProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    
    private static final Logger LOGGER = LogFactory.getLogger();
    
    public static void main(String[] args) throws Exception {
    
        final Configuration configuration = new Configuration();
        final MainProcessor mainProcessor = configuration.mainProcessor();
    
        BufferedReader bufferedReader = null;
        try {
            processFile(mainProcessor, getBufferedReader(args));
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    
    }
    
    private static BufferedReader getBufferedReader(final String[] args) throws IOException {
        if (args.length == 0) {
            final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            return new BufferedReader(new InputStreamReader(contextClassLoader.getResourceAsStream("message.fix"), "UTF-8"));
        } else {
            final Path path = Paths.get(args[0]);
            return Files.newBufferedReader(path);
        }
    }
    
    private static void processFile(final MainProcessor mainProcessor, final BufferedReader reader) throws IOException {
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
