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

    private final static String reportFormat = "Product: {0} Number of Sales: {1} Total Value: {2}";

    private final Map<String, List<Sale>> sales;
    private final List<SaleMessage> messagesLog;

    public MessageAggregatorImpl() {
        this.sales = new HashMap<>();
        this.messagesLog = new ArrayList<>();
    }

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

        Sale sale;

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
        System.out.println("Sales Report:\n");
        sales.keySet().forEach( product -> {
            long totalNumberOfSales = 0;
            double totalValue = 0;
            List<Sale> listOfSales = sales.get(product);
            for (Sale sale : listOfSales) {
                totalNumberOfSales += sale.getNumberOfSales();
                totalValue += sale.getTotalAdjustedValue();
            }
            System.out.println(MessageFormat.format(reportFormat, product, totalNumberOfSales, totalValue));
        });
        System.out.println("\n\n");
    }

    public void printAdjustmentReport() {
        System.out.println("Adjustment Report:\n");
        sales.keySet().forEach( product -> {
            List<Sale> listOfSales = sales.get(product);
            listOfSales.forEach(System.out::println);
        });
    }

    public Map<String, List<Sale>> getSales() {
        return this.sales;
    }
}