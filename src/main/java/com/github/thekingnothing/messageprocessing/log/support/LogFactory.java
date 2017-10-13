package com.github.thekingnothing.messageprocessing.log.support;


import com.github.thekingnothing.messageprocessing.log.LogImplementer;
import com.github.thekingnothing.messageprocessing.log.Logger;

public class LogFactory {
    
    private static LogImplementer factory = new ConsoleLogImplementer();
    
    public static Logger getLogger(){
        return factory.getLogger();
    }
}
