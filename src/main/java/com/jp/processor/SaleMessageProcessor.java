package com.jp.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.aggregator.MessageAggregator;
import com.jp.message.SaleMessage;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class SaleMessageProcessor implements MessageProcessor {

    private final static int NUMBER_OF_MESSAGES_TO_PRINT_REPORT = 10;
    private final static int NUMBER_OF_MESSAGES_TO_STOP = 50;
    private final MessageAggregator messageAggregator;
    private int numberOfMessages;

    public SaleMessageProcessor(MessageAggregator messageAggregator) {
        this.messageAggregator = messageAggregator;
    }

    public void process(String fileName) {
        File salesNotificationsFile = openFile(fileName);
        Scanner scanner = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            scanner = new Scanner(salesNotificationsFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SaleMessage saleMessage = mapper.readValue(line, SaleMessage.class);
                try {
                    this.messageAggregator.aggregateMessage(saleMessage);
                    this.numberOfMessages++;
                    if (numberOfMessages % NUMBER_OF_MESSAGES_TO_PRINT_REPORT == 0) {
                       this.messageAggregator.printReport();
                    }
                    if (numberOfMessages == NUMBER_OF_MESSAGES_TO_STOP) {
                        this.messageAggregator.printAdjustmentReport();
                       return;
                    }
                }
                catch(Exception e) {
                    System.out.println("Can't process message " + saleMessage);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Opens a file specified by fileName
     * @param fileName File Name
     * @return File
     */
    private File openFile(String fileName) {

        if(fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Incorrect input file name " + fileName);
        }

        URL url = getClass().getClassLoader().getResource(fileName);

        if(url == null) {
            throw new IllegalArgumentException("Can't open file " + fileName);
        }

        return new File(url.getFile());
    }
}
