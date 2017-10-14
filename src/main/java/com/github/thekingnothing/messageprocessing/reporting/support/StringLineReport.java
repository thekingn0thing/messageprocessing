package com.github.thekingnothing.messageprocessing.reporting.support;

import com.github.thekingnothing.messageprocessing.reporting.Report;

import java.util.ArrayList;
import java.util.List;

public class StringLineReport implements Report {
    private final List<String> lines;
    
    public StringLineReport() {
        lines = new ArrayList<>();
    }
    
    @Override
    public List<String> lines() {
        return lines;
    }
    
    public void append(String line){
        lines.add(line);
    }
}
