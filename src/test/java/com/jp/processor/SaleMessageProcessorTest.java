package com.jp.processor;

import com.jp.aggregator.MessageAggregator;
import com.jp.aggregator.MessageAggregatorImpl;
import com.jp.message.SaleMessage;
import com.jp.model.Adjustment;
import com.jp.model.AdjustmentOperation;
import com.jp.model.Sale;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SaleMessageProcessorTest {

    @Test
    public void testPrintReportInvocation() throws IOException {
        MessageAggregator messageAggregator = mock(MessageAggregator.class);
        MessageProcessor saleMessageProcessor = new SaleMessageProcessor(10, 50, messageAggregator, "sales_notification.txt");
        saleMessageProcessor.run();
        verify(messageAggregator, times(5)).printReport();
        verify(messageAggregator, times(1)).printAdjustmentReport();
    }

    @Test
    public void testComplexExample() throws IOException {
        MessageAggregator messageAggregator = new MessageAggregatorImpl();
        MessageProcessor saleMessageProcessor = new SaleMessageProcessor(10, 10, messageAggregator, "complex_test_example.txt");
        saleMessageProcessor.run();

        List<Sale> appleSales = messageAggregator.getSales().get("apple");
        List<Sale> bananaSales = messageAggregator.getSales().get("banana");
        assertNotNull(appleSales);
        assertNotNull(bananaSales);
        assertEquals(appleSales.size(), 2);
        assertEquals(bananaSales.size(), 2);

        Sale simpleAppleSale = appleSales.get(0);
        assertEquals(simpleAppleSale.getTotalAdjustedValue(), 26, 0.01);
        assertEquals(simpleAppleSale.getNumberOfSales(), 1);
        assertEquals(simpleAppleSale.getInitialPrice(), 10, 0.01);
        assertEquals(simpleAppleSale.getAdjustedPrice(), 26, 0.01);
        assertAdjustment(simpleAppleSale.getAdjustments());

        Sale bulkAppleSale = appleSales.get(1);
        assertEquals(bulkAppleSale.getTotalAdjustedValue(), 520, 0.01);
        assertEquals(bulkAppleSale.getNumberOfSales(), 20);
        assertEquals(bulkAppleSale.getInitialPrice(), 10, 0.01);
        assertEquals(bulkAppleSale.getAdjustedPrice(), 26, 0.01);
        assertAdjustment(bulkAppleSale.getAdjustments());

        Sale simpleBananaSale = bananaSales.get(0);
        assertEquals(simpleBananaSale.getTotalAdjustedValue(), 46, 0.01);
        assertEquals(simpleBananaSale.getNumberOfSales(), 1);
        assertEquals(simpleBananaSale.getInitialPrice(), 20, 0.01);
        assertEquals(simpleBananaSale.getAdjustedPrice(), 46, 0.01);
        assertAdjustment(simpleBananaSale.getAdjustments());

        Sale bulkBananaSale = bananaSales.get(1);
        assertEquals(bulkBananaSale.getTotalAdjustedValue(), 920, 0.01);
        assertEquals(bulkBananaSale.getNumberOfSales(), 20);
        assertEquals(bulkBananaSale.getInitialPrice(), 20, 0.01);
        assertEquals(bulkBananaSale.getAdjustedPrice(), 46, 0.01);
        assertAdjustment(bulkBananaSale.getAdjustments());
    }

    private void assertAdjustment(List<Adjustment> adjustments) {
        assertNotNull(adjustments);
        assertEquals(adjustments.size(), 3);
        Adjustment addAdjustment = adjustments.get(0);
        Adjustment subtractAdjustment = adjustments.get(1);
        Adjustment multiplyAdjustment = adjustments.get(2);

        assertEquals(addAdjustment.getOperation(), AdjustmentOperation.ADD);
        assertEquals(addAdjustment.getValue(), 5, 0.01);
        assertEquals(subtractAdjustment.getOperation(), AdjustmentOperation.SUBTRACT);
        assertEquals(subtractAdjustment.getValue(), 2, 0.01);
        assertEquals(multiplyAdjustment.getOperation(), AdjustmentOperation.MULTIPLY);
        assertEquals(multiplyAdjustment.getValue(), 2, 0.01);
    }
}
