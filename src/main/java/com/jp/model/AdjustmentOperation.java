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
            double result = priceBeforeAdjustment - value;
            if (result < 0) {
                throw new RuntimeException("Incorrect Adjustment, price can't be below zero");
            }

            return result;
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
