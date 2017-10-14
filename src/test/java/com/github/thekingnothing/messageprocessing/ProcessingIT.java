package com.github.thekingnothing.messageprocessing;

import com.github.thekingnothing.messageprocessing.exception.RejectionException;
import com.github.thekingnothing.messageprocessing.mock.MockLogger;
import com.github.thekingnothing.messageprocessing.model.AdjustmentType;
import com.github.thekingnothing.messageprocessing.model.Order;
import com.github.thekingnothing.messageprocessing.processing.processor.MainProcessor;
import com.github.thekingnothing.messageprocessing.storage.Storage;
import com.github.thekingnothing.messageprocessing.storage.support.DefaultStorage;
import com.github.thekingnothing.messageprocessing.test.WithMessage;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

public class ProcessingIT implements WithMessage {
    
    @Test
    public void test_should_process_all_message_and_print_report_after_n_and_m_message_and_stop_processing() {
        final Storage storage = new DefaultStorage();
    
        final int orderReportPeriod = 2;
        final int adjustmentReportPeriod = 5;
        final int messageLimit = 5;
    
        final MockLogger logger = new MockLogger();
    
        final MainProcessor mainProcessor = createMainProcessor(storage, orderReportPeriod, adjustmentReportPeriod, messageLimit, logger);
    
        send5DifferentMessages(mainProcessor);
    
        assertThatAllOrdersAreRecordered(storage);
        assertThatReportDetailingNumberOfOrderOfEachSymbolAndTotalValueHasBeenPrint(logger);
        assertThatReportOfTheAdjustmentMadeForEachSymbolHasBeenPrint(logger);
    
        assertThatMessageProcessingIsPaused(mainProcessor);
    }
    
    private MainProcessor createMainProcessor(final Storage storage, final int orderReportPeriod, final int adjustmentReportPeriod,
                                              final int messageLimit, final MockLogger logger) {
        final ProcessorConfiguration configuration = new ProcessorConfiguration(orderReportPeriod, adjustmentReportPeriod, messageLimit);
        return configuration.mainProcessor(storage, configuration.routeProcessor(storage), logger);
    }
    
    private void assertThatMessageProcessingIsPaused(final MainProcessor mainProcessor) {
        assertThatThrownBy(() -> mainProcessor.process(createSingleOrderMessage("AAA", BigDecimal.ONE)))
            .as("Message processing is paused")
            .isInstanceOf(RejectionException.class);
    }
    
    private void assertThatAllOrdersAreRecordered(final Storage storage) {
        assertThat(storage.allOrders())
            .as("All orders have be recorded and processed.")
            .hasSize(4)
            .extracting(Order::getSymbol, Order::getPrice)
            .containsExactlyInAnyOrder(
                tuple("IBM", new BigDecimal("4.5")),
                tuple("IBM", new BigDecimal("5.5")),
                tuple("IBM", new BigDecimal("5.0")),
                tuple("APL", new BigDecimal("20.5"))
            );
    }
    
    private void send5DifferentMessages(final MainProcessor mainProcessor) {
        mainProcessor.process(createSingleOrderMessage("IBM", new BigDecimal("2")));
        mainProcessor.process(createQuantityOrderMessage("IBM", new BigDecimal(50), new BigDecimal("3")));
        mainProcessor.process(createSingleOrderMessage("IBM", new BigDecimal("2.5")));
        mainProcessor.process(createAdjustmentMessage("IBM", AdjustmentType.ADD.getOperationType(), new BigDecimal("2.5")));
        mainProcessor.process(createQuantityOrderMessage("APL", BigDecimal.TEN, new BigDecimal("20.5")));
    }
    
    private void assertThatReportOfTheAdjustmentMadeForEachSymbolHasBeenPrint(final MockLogger logger) {
        logger.assertLogContains("Adjustments for IBM: +2.5");
    }
    
    private void assertThatReportDetailingNumberOfOrderOfEachSymbolAndTotalValueHasBeenPrint(final MockLogger logger) {
        logger.assertLogContains("| IBM | 51 | 152 |");
        logger.assertLogContains("| IBM | 52 | 284.5 |");
    }
    
}
