package com.github.thekingnothing.messageprocessing.report.support;

import com.github.thekingnothing.messageprocessing.report.Report;

import java.util.ArrayList;
import java.util.List;

class StringLineReport implements Report {
    private final List<String> lines;
    
    StringLineReport() {
        lines = new ArrayList<>();
    }
    
    @Override
    public List<String> lines() {
        return lines;
    }
    
    void append(String line){
        lines.add(line);
    }
}
