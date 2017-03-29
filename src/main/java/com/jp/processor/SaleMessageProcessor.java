package com.jp.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.aggregator.MessageAggregator;
import com.jp.message.SaleMessage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SaleMessageProcessor implements MessageProcessor {

    private final int numberOfMessagesToPrintReport;
    private final int numberOfMessagesToStop;
    private final MessageAggregator messageAggregator;
    private final String fileName;

    private int numberOfMessages;

    public SaleMessageProcessor(int numberOfMessagesToPrintReport,
                                int numberOfMessagesToStop,
                                MessageAggregator messageAggregator,
                                String fileName) {
        this.numberOfMessagesToPrintReport = numberOfMessagesToPrintReport;
        this.numberOfMessagesToStop = numberOfMessagesToStop;
        this.messageAggregator = messageAggregator;
        this.fileName = fileName;
    }

    public void run() throws IOException {
        File salesNotificationsFile = openFile();
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = null;
        try {
            scanner = new Scanner(salesNotificationsFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SaleMessage saleMessage = mapper.readValue(line, SaleMessage.class);
                try {
                    this.messageAggregator.aggregateMessage(saleMessage);
                    this.numberOfMessages++;
                    if (numberOfMessages % numberOfMessagesToPrintReport == 0) {
                       this.messageAggregator.printReport();
                    }
                    if (numberOfMessages == numberOfMessagesToStop) {
                        System.out.println("Stop accepting messages\n");
                        this.messageAggregator.printAdjustmentReport();
                        return;
                    }
                }
                catch(Exception e) {
                    System.out.println("Can't process message " + saleMessage);
                    e.printStackTrace();
                }
            }
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
    }

    private File openFile() {

        if(this.fileName == null || this.fileName.isEmpty()) {
            throw new IllegalArgumentException("Incorrect input file name " + this.fileName);
        }

        URL url = getClass().getClassLoader().getResource(this.fileName);

        if(url == null) {
            throw new IllegalArgumentException("Can't open file " + this.fileName);
        }

        return new File(url.getFile());
    }
}
