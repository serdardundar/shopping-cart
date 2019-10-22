package com.ecommerce.domain;

import com.ecommerce.enumeration.DiscountType;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author serdardundar
 * @since 20/10/2019
 */
@Getter
public class Coupon {

    private BigDecimal minPurchaseAmount;
    private BigDecimal discountProfit;
    private DiscountType discountType;

    public Coupon(BigDecimal minPurchaseAmount, BigDecimal discountProfit, DiscountType discountType) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.discountProfit = discountProfit;
        this.discountType = discountType;
    }
}
