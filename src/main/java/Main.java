import com.jp.aggregator.MessageAggregatorImpl;
import com.jp.processor.SaleMessageProcessor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SaleMessageProcessor processor;
        try {
            processor = new SaleMessageProcessor(10, 50, new MessageAggregatorImpl(), "sales_notification.txt");
            processor.run();
        } catch (IOException e) {
            System.out.println("Application execution failed");
            e.printStackTrace();
        }
    }
}
