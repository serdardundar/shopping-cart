package com.ecommerce.enumeration;

/**
 * @author serdardundar
 * @since 19/10/2019
 */
public enum DiscountType {
    Rate("rateDiscountCalculator"),
    Amount("amountDiscountCalculator");

    private final String value;

    DiscountType(String type) {
        this.value = type;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
