# sales-message-processor
It is a small message processing application for processing sales notification messages.
It reads messages in JSON format from a file.
To run the applicaiton you need to run main method of Main class. It will create an instance of SaleMessageProcessor and invoke run method.
For example, 
```java
SaleMessageProcessor processor = new SaleMessageProcessor(10, 50, new MessageAggregatorImpl(), "sales_notification.txt");
processor.run();
```
SaleMessageProcessor accepts four parameters in a constructor:
* numberOfMessagesToPrintReport - after every numberOfMessagesToPrintReport message received this application will log a report detailing the number of sales of each product and their total value.
* numberOfMessagesToStop - after numberOfMessagesToStop messages this application will log that it is pausing, stop accepting new messages and log a report of the adjustments that have been made to each sale type while the application was running. 
* messageAggregator - MessageAggregator Implementation
* fileName - the name of the file with messages in JSON format
# Message Format
There are three types of messages:
* SaleMessage, example: {"@type" : "SaleMessage", "product" : "apple", "price" : "10"}. It represents one sale of apples at 10p
* BulkSaleMessage, example: {"@type" : "BulkSaleMessage", "product" : "apple", "price" : "10", "numberOfSales" : "20"}. It represents 20 sales of apples at 20p each.
* AdjustmentSaleMessage, {"@type" : "AdjustmentSaleMessage", "product" : "apple", "price" : "10", "adjustmentOperation" : "ADD"}. It represents adjustment message. All prices will be increased on 20p for all recordered sales of apples.
# Reports
After every numberOfMessagesToPrintReport messages application will log a sales report to console.
Example of such report:
```
Sales Report:

Product: banana Number of Sales: 8 Total Value: 80
Product: apple Number of Sales: 132 Total Value: 4 340
```
After numberOfMessagesToStop application will log an adjustment report. It contains all sale operations with all applied adjustments.
Example:
```
Adjustment Report:

Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='banana', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=10.0, adjustedPrice=10.0, adjustments=null}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=55.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=20, initialPrice=10.0, totalAdjustedValue=1100.0, adjustedPrice=55.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=20, initialPrice=10.0, totalAdjustedValue=700.0, adjustedPrice=35.0, adjustments=[Adjustment{operation=MULTIPLY, value=2.0}, Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=25.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=20, initialPrice=10.0, totalAdjustedValue=500.0, adjustedPrice=25.0, adjustments=[Adjustment{operation=SUBTRACT, value=5.0}, Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=30.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=20, initialPrice=10.0, totalAdjustedValue=600.0, adjustedPrice=30.0, adjustments=[Adjustment{operation=ADD, value=10.0}, Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=1, initialPrice=10.0, totalAdjustedValue=20.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
Sale{product='apple', numberOfSales=20, initialPrice=10.0, totalAdjustedValue=400.0, adjustedPrice=20.0, adjustments=[Adjustment{operation=ADD, value=10.0}]}
```
