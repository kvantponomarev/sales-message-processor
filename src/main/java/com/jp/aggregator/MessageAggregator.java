package com.jp.aggregator;

import com.jp.message.AdjustmentSaleMessage;
import com.jp.message.BulkSaleMessage;
import com.jp.message.SaleMessage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAggregator {

    private final static String reportFormat = "Product: {0}\tNumber of Sales: {1}\tTotal Value: {2}";

    private final Map<String, SaleDetails> productToSaleDetails = new HashMap<String, SaleDetails>();
    private final Map<String, List<SaleMessage>> messages = new HashMap<String, List<SaleMessage>>();

    public void processMessage(SaleMessage saleMessage) {

        if (saleMessage == null) {
            throw new IllegalArgumentException("Message is null");
        }

        String product = saleMessage.getProduct();
        if(product == null || product.isEmpty()) {
            throw new IllegalArgumentException("Incorrect product type " + product);
        }

        if(!messages.containsKey(product)) {
            messages.put(product, new ArrayList<SaleMessage>());
        }
        messages.get(product).add(saleMessage);

        if (saleMessage instanceof BulkSaleMessage) {
            BulkSaleMessage bulkSaleMessage = (BulkSaleMessage) saleMessage;
            if (!productToSaleDetails.containsKey(product)) {
                productToSaleDetails.put(product, new SaleDetails(bulkSaleMessage.getNumberOfSales(), bulkSaleMessage.getValue()));
            } else {
                productToSaleDetails.get(product).addSalesInformation(bulkSaleMessage.getNumberOfSales(), bulkSaleMessage.getValue());
            }
        } else if(saleMessage instanceof AdjustmentSaleMessage) {
            AdjustmentSaleMessage adjustmentSaleMessage = (AdjustmentSaleMessage) saleMessage;

        } else {
            if (!productToSaleDetails.containsKey(product)) {
                productToSaleDetails.put(product, new SaleDetails(1, saleMessage.getValue()));
            } else {
                productToSaleDetails.get(product).addSalesInformation(1, saleMessage.getValue());
            }
        }

    }

    public void printReport(){
        for (String product : productToSaleDetails.keySet()) {
            SaleDetails saleDetail = productToSaleDetails.get(product);
            if(saleDetail != null) {
                System.out.print(MessageFormat.format(reportFormat, product, saleDetail.getNumberOfSales(), saleDetail.getTotalValue()));
            }
        }
    }
}