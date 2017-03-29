package com.jp.model;

public class Adjustment {

    private final AdjustmentOperation operation;
    private final double value;

    public Adjustment(AdjustmentOperation operation, double value) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation can't be null for Adjustment");
        }
        this.operation = operation;
        this.value = value;
    }

    public AdjustmentOperation getOperation() {
        return operation;
    }

    public double getValue() {
        return value;
    }

    public double adjustPrice(double priceBeforeAdjustment) {
        return this.operation.adjust(priceBeforeAdjustment, value);
    }

    @Override
    public String toString() {
        return "Adjustment{" + "operation=" + operation + ", value=" + value + '}';
    }
}
