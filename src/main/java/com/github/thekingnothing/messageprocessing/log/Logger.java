package com.github.thekingnothing.messageprocessing.log;

public interface Logger {
    
    void info(String message);
    
    void info(String message, Object... args);
    
    void error(String message, Throwable e);
}
