package com.jp.aggregator;

import com.jp.message.AdjustmentSaleMessage;
import com.jp.message.BulkSaleMessage;
import com.jp.message.SaleMessage;
import com.jp.model.Adjustment;
import com.jp.model.AdjustmentOperation;
import com.jp.model.Sale;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAggregatorImpl implements MessageAggregator {

    private final static String reportFormat = "Product: {0}\tNumber of Sales: {1}\tTotal Value: {2}";

    private final Map<String, List<Sale>> sales = new HashMap<>();
    private final List<SaleMessage> messagesLog = new ArrayList<>();

    public void aggregateMessage(SaleMessage saleMessage) {

        //Save raw message
        messagesLog.add(saleMessage);

        if (saleMessage == null) {
            throw new IllegalArgumentException("Message is null");
        }

        String product = saleMessage.getProduct();

        if(product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Incorrect product type " + product);
        }

        if(!sales.containsKey(product)) {
            sales.put(product, new ArrayList<>());
        }

        Sale sale = null;

        if(saleMessage instanceof BulkSaleMessage) {
            sale = new Sale(product, ((BulkSaleMessage)saleMessage).getNumberOfSales(), saleMessage.getPrice());
        } else if (saleMessage instanceof AdjustmentSaleMessage) {
            AdjustmentOperation operation = ((AdjustmentSaleMessage) saleMessage).getAdjustmentOperation();
            if (operation == null) {
                throw new IllegalArgumentException("Null Adjustment operation");
            }
            Adjustment adjustment = new Adjustment(operation, saleMessage.getPrice());
            sales.get(product).forEach(saleToAdjust -> {
                saleToAdjust.addAdjustment(adjustment);
            });
            return;
        } else {
            sale = new Sale(product, 1, saleMessage.getPrice());
        }


        sales.get(product).add(sale);
    }

    public void printReport() {
        for (String product : sales.keySet()) {
            long totalNumberOfSales = 0;
            double totalValue = 0;
            List<Sale> listOfSales = sales.get(product);
            for (Sale sale : listOfSales) {
                totalNumberOfSales += sale.getNumberOfSales();
                totalValue += sale.getTotalAdjustedValue();
            }
            System.out.println(MessageFormat.format(reportFormat, product, totalNumberOfSales, totalValue));
        }
    }

    public void printAdjustmentReport() {
        for (String product : sales.keySet()) {
            List<Sale> listOfSales = sales.get(product);
            for (Sale sale : listOfSales) {
                System.out.println(sale);
            }
        }
    }
}