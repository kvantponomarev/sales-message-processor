package com.jp.message;

public class AdjustmentSaleMessage extends SaleMessage {

    private AdjustmentOperation adjustmentOperation;

    public AdjustmentOperation getAdjustmentOperation() {
        return adjustmentOperation;
    }

    public void setAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        this.adjustmentOperation = adjustmentOperation;
    }
}
