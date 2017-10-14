package com.github.thekingnothing.messageprocessing.processing;

import com.github.thekingnothing.messageprocessing.log.Logger;
import com.github.thekingnothing.messageprocessing.message.MessageType;
import com.github.thekingnothing.messageprocessing.processing.Processor;
import com.github.thekingnothing.messageprocessing.processing.processor.AdjustmentProcessor;
import com.github.thekingnothing.messageprocessing.processing.processor.MainProcessor;
import com.github.thekingnothing.messageprocessing.processing.processor.QuantiedOrderProcessor;
import com.github.thekingnothing.messageprocessing.processing.processor.ReportPostProcessor;
import com.github.thekingnothing.messageprocessing.processing.processor.RouteProcessor;
import com.github.thekingnothing.messageprocessing.processing.processor.SingleOrderProcessor;
import com.github.thekingnothing.messageprocessing.reporting.reports.AdjustmentReportFactory;
import com.github.thekingnothing.messageprocessing.reporting.out.support.LogReportPrinter;
import com.github.thekingnothing.messageprocessing.reporting.reports.OrderReportFactory;
import com.github.thekingnothing.messageprocessing.storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class ProcessorConfiguration {
    
    private int orderReportPeriod;
    private int adjustmentReportPeriod;
    private int messageLimit;
    
    public ProcessorConfiguration(final int orderReportPeriod, final int adjustmentReportPeriod, final int messageLimit) {
        this.orderReportPeriod = orderReportPeriod;
        this.adjustmentReportPeriod = adjustmentReportPeriod;
        this.messageLimit = messageLimit;
    }
    
    public RouteProcessor routeProcessor(Storage storage) {
        Map<MessageType, Processor> mapping = new HashMap<>();
        mapping.put(MessageType.SINGLE_ORDER, new SingleOrderProcessor(storage));
        mapping.put(MessageType.QUANTITY_ORDER, new QuantiedOrderProcessor(storage));
        mapping.put(MessageType.ADJUSTMENT, new AdjustmentProcessor(storage));
        return new RouteProcessor(mapping);
    }
    
    public MainProcessor mainProcessor(final Storage storage,
                                       final Processor routeProcessor,
                                       final Logger logger) {
        final MainProcessor mainProcessor = new MainProcessor(storage, routeProcessor, messageLimit);
    
        mainProcessor.addPostProcessor(new ReportPostProcessor(orderReportPeriod, new OrderReportFactory(), new LogReportPrinter(logger)));
        mainProcessor.addPostProcessor(new ReportPostProcessor(adjustmentReportPeriod, new AdjustmentReportFactory(), new LogReportPrinter(logger)));
        
        return mainProcessor;
    }
}
