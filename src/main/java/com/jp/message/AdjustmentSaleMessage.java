package com.jp.message;

import com.jp.model.AdjustmentOperation;

public class AdjustmentSaleMessage extends SaleMessage {

    private AdjustmentOperation adjustmentOperation;

    public AdjustmentOperation getAdjustmentOperation() {
        return adjustmentOperation;
    }

    public void setAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        this.adjustmentOperation = adjustmentOperation;
    }
}
