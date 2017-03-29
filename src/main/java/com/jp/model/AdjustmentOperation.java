package com.jp.model;

public enum AdjustmentOperation {
    ADD {
        @Override
        public double adjust(double priceBeforeAdjustment, double value) {
            return priceBeforeAdjustment + value;
        }
    },
    SUBTRACT {
        @Override
        public double adjust(double priceBeforeAdjustment, double value) {
            return priceBeforeAdjustment - value;
        }
    },
    MULTIPLY {
        @Override
        public double adjust(double priceBeforeAdjustment, double value) {
            return priceBeforeAdjustment * value;
        }
    };

    public abstract double adjust(double priceBeforeAdjustment, double value);
}
