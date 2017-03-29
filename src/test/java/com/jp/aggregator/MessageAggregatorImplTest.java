package com.jp.aggregator;

import com.jp.message.AdjustmentSaleMessage;
import com.jp.message.BulkSaleMessage;
import com.jp.message.SaleMessage;
import com.jp.model.Adjustment;
import com.jp.model.AdjustmentOperation;
import com.jp.model.Sale;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MessageAggregatorImplTest {

    private MessageAggregator messageAggregator;

    @Before
    public void setUp() {
        this.messageAggregator = new MessageAggregatorImpl();
    }

    @Test
    public void testAggregateSimpleSaleMessage() {
        SaleMessage saleMessage = new SaleMessage();
        saleMessage.setPrice(10);
        saleMessage.setProduct("apple");
        this.messageAggregator.aggregateMessage(saleMessage);
        Map<String, List<Sale>> sales = this.messageAggregator.getSales();
        assertNotNull(sales);
        List<Sale> listOfSales = sales.get("apple");
        assertNotNull(listOfSales);
        assertEquals(listOfSales.size(), 1);
        Sale sale = listOfSales.get(0);
        assertNotNull(sale);
        assertEquals(sale.getProduct(), "apple");
        assertEquals(sale.getInitialPrice(), 10, 0.01);
        assertEquals(sale.getNumberOfSales(), 1);
        assertEquals(sale.getAdjustedPrice(), 10, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), 10, 0.01);
        assertNull(sale.getAdjustments());
    }


    @Test
    public void testAggregateBulkSaleMessage() {
        BulkSaleMessage bulkSaleMessage = new BulkSaleMessage();
        bulkSaleMessage.setPrice(10);
        bulkSaleMessage.setProduct("apple");
        bulkSaleMessage.setNumberOfSales(20);
        this.messageAggregator.aggregateMessage(bulkSaleMessage);
        Map<String, List<Sale>> sales = this.messageAggregator.getSales();
        assertNotNull(sales);
        List<Sale> listOfSales = sales.get("apple");
        assertNotNull(listOfSales);
        assertEquals(listOfSales.size(), 1);
        Sale sale = listOfSales.get(0);
        assertNotNull(sale);
        assertEquals(sale.getProduct(), "apple");
        assertEquals(sale.getInitialPrice(), 10, 0.01);
        assertEquals(sale.getNumberOfSales(), 20);
        assertEquals(sale.getAdjustedPrice(), 10, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), 200, 0.01);
        assertNull(sale.getAdjustments());
    }

    @Test
    public void testAdjustmentAddOnSimpleSaleMessage() {
        testAdjustmentOnSimpleSaleMessage(AdjustmentOperation.ADD, 10, 5, 15);
    }

    @Test
    public void testAdjustmentSubtractOnSimpleSaleMessage() {
        testAdjustmentOnSimpleSaleMessage(AdjustmentOperation.SUBTRACT, 10, 5, 5);
    }

    @Test
    public void testAdjustmentMultiplyOnSimpleSaleMessage() {
        testAdjustmentOnSimpleSaleMessage(AdjustmentOperation.SUBTRACT, 10, 5, 5);
    }

    private void testAdjustmentOnSimpleSaleMessage(AdjustmentOperation adjustmentOperation, double initialPrice, double adjustedValue, double expectedAdjustedPrice) {

        SaleMessage saleMessage = new SaleMessage();
        saleMessage.setPrice(initialPrice);
        saleMessage.setProduct("apple");

        this.messageAggregator.aggregateMessage(saleMessage);

        AdjustmentSaleMessage adjustmentSaleMessage = new AdjustmentSaleMessage();
        adjustmentSaleMessage.setAdjustmentOperation(adjustmentOperation);
        adjustmentSaleMessage.setPrice(adjustedValue);
        adjustmentSaleMessage.setProduct("apple");

        this.messageAggregator.aggregateMessage(adjustmentSaleMessage);

        Map<String, List<Sale>> sales = this.messageAggregator.getSales();
        assertNotNull(sales);
        List<Sale> listOfSales = sales.get("apple");
        assertNotNull(listOfSales);
        assertEquals(listOfSales.size(), 1);
        Sale sale = listOfSales.get(0);
        assertNotNull(sale);
        assertEquals(sale.getProduct(), "apple");
        assertEquals(sale.getInitialPrice(), initialPrice, 0.01);
        assertEquals(sale.getNumberOfSales(), 1);
        assertEquals(sale.getAdjustedPrice(), expectedAdjustedPrice, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), expectedAdjustedPrice, 0.01);
        List<Adjustment> adjustments = sale.getAdjustments();
        assertNotNull(adjustments);
        assertEquals(adjustments.size(), 1);
        Adjustment adjustment = adjustments.get(0);

        assertEquals(adjustment.getValue(), adjustedValue, 0.01);
        assertEquals(adjustment.getOperation(), adjustmentOperation);
    }

    @Test
    public void testAdjustmentAddOnBulkSaleMessage() {
        testAdjustmentOnBulkSaleMessage(AdjustmentOperation.ADD, 10, 20, 5, 15);
    }

    @Test
    public void testAdjustmentSubtractOnBulkSaleMessage() {
        testAdjustmentOnBulkSaleMessage(AdjustmentOperation.SUBTRACT, 10, 20, 5, 5);
    }

    @Test
    public void testAdjustmentMultiplyOnBulkSaleMessage() {
        testAdjustmentOnBulkSaleMessage(AdjustmentOperation.SUBTRACT, 10, 20, 5, 5);
    }

    private void testAdjustmentOnBulkSaleMessage(AdjustmentOperation adjustmentOperation,
                                                double initialPrice,
                                                long numberOfSales,
                                                double adjustedValue,
                                                double expectedAdjustedPrice) {

        BulkSaleMessage bulkSaleMessage = new BulkSaleMessage();
        bulkSaleMessage.setPrice(initialPrice);
        bulkSaleMessage.setProduct("apple");
        bulkSaleMessage.setNumberOfSales(numberOfSales);

        this.messageAggregator.aggregateMessage(bulkSaleMessage);

        AdjustmentSaleMessage adjustmentSaleMessage = new AdjustmentSaleMessage();
        adjustmentSaleMessage.setAdjustmentOperation(adjustmentOperation);
        adjustmentSaleMessage.setPrice(adjustedValue);
        adjustmentSaleMessage.setProduct("apple");

        this.messageAggregator.aggregateMessage(adjustmentSaleMessage);

        Map<String, List<Sale>> sales = this.messageAggregator.getSales();
        assertNotNull(sales);
        List<Sale> listOfSales = sales.get("apple");
        assertNotNull(listOfSales);
        assertEquals(listOfSales.size(), 1);
        Sale sale = listOfSales.get(0);
        assertNotNull(sale);
        assertEquals(sale.getProduct(), "apple");
        assertEquals(sale.getInitialPrice(), initialPrice, 0.01);
        assertEquals(sale.getNumberOfSales(), numberOfSales);
        assertEquals(sale.getAdjustedPrice(), expectedAdjustedPrice, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), expectedAdjustedPrice * numberOfSales, 0.01);
        List<Adjustment> adjustments = sale.getAdjustments();
        assertNotNull(adjustments);
        assertEquals(adjustments.size(), 1);
        Adjustment adjustment = adjustments.get(0);

        assertEquals(adjustment.getValue(), adjustedValue, 0.01);
        assertEquals(adjustment.getOperation(), adjustmentOperation);
    }

    @Test
    public void testAdjustmentOnDifferentProduct() {
        SaleMessage saleMessage = new SaleMessage();
        saleMessage.setPrice(10);
        saleMessage.setProduct("apple");

        this.messageAggregator.aggregateMessage(saleMessage);

        AdjustmentSaleMessage adjustmentSaleMessage = new AdjustmentSaleMessage();
        adjustmentSaleMessage.setAdjustmentOperation(AdjustmentOperation.ADD);
        adjustmentSaleMessage.setPrice(10);
        adjustmentSaleMessage.setProduct("banana");

        this.messageAggregator.aggregateMessage(adjustmentSaleMessage);

        Map<String, List<Sale>> sales = this.messageAggregator.getSales();
        assertNotNull(sales);
        List<Sale> listOfSales = sales.get("apple");
        assertNotNull(listOfSales);
        assertEquals(listOfSales.size(), 1);
        Sale sale = listOfSales.get(0);
        assertNotNull(sale);

        assertNull(sale.getAdjustments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMessage() {
        this.messageAggregator.aggregateMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullProduct() {
        SaleMessage saleMessage = new SaleMessage();
        this.messageAggregator.aggregateMessage(saleMessage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyProduct() {
        SaleMessage saleMessage = new SaleMessage();
        saleMessage.setProduct("");
        this.messageAggregator.aggregateMessage(saleMessage);
    }
}
