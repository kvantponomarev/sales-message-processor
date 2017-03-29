import com.jp.aggregator.MessageAggregatorImpl;
import com.jp.processor.SaleMessageProcessor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SaleMessageProcessor processor = new SaleMessageProcessor(new MessageAggregatorImpl());
        processor.process("sales_notification.txt");
    }
}
