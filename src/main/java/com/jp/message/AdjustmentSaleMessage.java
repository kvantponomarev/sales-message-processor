package com.jp.message;

public class AdjustmentSaleMessage extends SaleMessage {

    private AdjustmentOperation adjustmentOperation;

    public AdjustmentOperation getAdjustmentOperation() {
        return adjustmentOperation;
    }

    public void setAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        this.adjustmentOperation = adjustmentOperation;
    }

    public String toString() {
        return "Adjustment{"+
                "product=" + product +
                ", price=" + price +
                ", adjustmentOperation = " + adjustmentOperation +
                '}';
    }
}
