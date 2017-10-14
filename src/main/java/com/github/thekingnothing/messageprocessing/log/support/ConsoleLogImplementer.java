package com.github.thekingnothing.messageprocessing.log.support;


import com.github.thekingnothing.messageprocessing.log.LogImplementer;
import com.github.thekingnothing.messageprocessing.log.Logger;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogImplementer implements LogImplementer {
    @Override
    public Logger getLogger() {
        return new ConsoleLogger();
    }
    
    private class ConsoleLogger implements Logger {
        private final DateTimeFormatter dateTimeFormatter;
        private final MessageFormat logFormat;
    
        private ConsoleLogger() {
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            logFormat = new MessageFormat("{0} | {1} | {2}");
        }
    
        @Override
        public void info(final String message) {
            final String log = formatLog(message, "INFO");
            log(log);
        }
    
        @Override
        public void info(final String message, final Object... args) {
            info(String.format(message, args));
        }
    
        @Override
        public void error(final String message, final Throwable e) {
            final String log = formatLog(message, "ERROR");
            log(log);
            e.printStackTrace(System.out);
        }
    
        private String formatLog(final String message, final String level) {
            return logFormat.format(new Object[]{time(), level, message});
        }
    
        private String time() {
            return dateTimeFormatter.format(LocalDateTime.now());
        }
    
        private void log(final String log) {
            System.out.println(log);
        }
    }
}
